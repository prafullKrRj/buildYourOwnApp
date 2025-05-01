package com.prafullkumar.propvault.admin.features.unit.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.core.model.DealWithPayments
import com.prafullkumar.propvault.admin.features.deals.data.toDeal
import com.prafullkumar.propvault.admin.features.deals.data.toPayment
import com.prafullkumar.propvault.admin.features.unit.domain.UnitRepository
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent

class UnitRepositoryImpl : UnitRepository, KoinComponent {

    private val db = FirebaseFirestore.getInstance()

    override fun addUnit(
        propertyUnit: PropertyUnit, deal: Deal?, payment: Payment?
    ): Flow<Resource<Boolean>> {
        Log.d("UnitRepositoryImpl", "addUnit: $propertyUnit")
        Log.d("UnitRepositoryImpl", "addUnit: $deal")
        Log.d("UnitRepositoryImpl", "addUnit: $payment")
        return flow {
            try {
                db.runTransaction { transaction ->
                    val unitRef = db.collection("units").document()
                    Log.d("UnitRepositoryImpl", "addUnit: $unitRef")


                    Log.d("UnitRepositoryImpl", "addUnit: ${propertyUnit.copy(id = unitRef.id)}")

                    transaction.update(
                        db.collection("buildings").document(propertyUnit.buildingId),
                        "units",
                        FieldValue.arrayUnion(unitRef.id)
                    )

                    val dealRef = deal?.let {
                        if (it.id.isEmpty()) {
                            db.collection("deals").document()
                        } else {
                            db.collection("deals").document(it.id)
                        }
                    }

                    val paymentRef = payment?.let {
                        if (it.id.isEmpty()) {
                            db.collection("payments").document()
                        } else {
                            db.collection("payments").document(it.id)
                        }
                    }

                    deal?.let {
                        if (it.id.isEmpty()) {
                            transaction.set(
                                dealRef!!, it.copy(id = dealRef.id, unitId = unitRef.id)
                            )
                        } else {
                            transaction.set(dealRef!!, it.copy(id = it.id, unitId = unitRef.id))
                        }
                    }

                    payment?.let {
                        if (it.id.isEmpty()) {
                            transaction.set(
                                paymentRef!!, it.copy(id = paymentRef.id, unitId = unitRef.id)
                            )
                        } else {
                            transaction.set(paymentRef!!, it.copy(id = it.id, unitId = unitRef.id))
                        }
                    }
                    transaction.set(unitRef, propertyUnit.copy(id = unitRef.id, deals = deal?.let {
                        listOf(it.id)
                    } ?: emptyList()))
                }.await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                Log.e("UnitRepositoryImpl", "addUnit: ${e.message}", e)
                emit(Resource.Error("Error adding unit"))
            }
        }
    }

    override fun getUnitDetails(unitId: String): Flow<Resource<UnitWithDealsAndPayments>> {
        return flow {
            try {
                val unitRef = db.collection("units").document(unitId).get().await().toPropertyUnit()
                val dealId = unitRef.deals.firstOrNull()
                val deal = dealId?.let {
                    db.collection("deals").document(it).get().await().toDeal()
                }
                val unitWithDetails = UnitWithDealsAndPayments(
                    unit = unitRef,
                    deals = deal?.let {
                        listOf(
                            DealWithPayments(
                                deal = it, payments = it.payments.mapNotNull { paymentId ->
                                    db.collection("payments").document(paymentId!!).get().await()
                                        .toPayment()
                                })
                        )
                    } ?: emptyList(),
                )
                emit(Resource.Success(unitWithDetails))
            } catch (e: Exception) {
                Log.d("UnitRepositoryImpl", "getUnitDetails: ${e.message}", e)
                emit(Resource.Error("Error fetching unit details"))
            }
        }
    }

    override fun updateUnit(
        propertyUnit: PropertyUnit, deal: Deal?, payment: Payment?
    ): Flow<Resource<Boolean>> {
        return flow {
            try {
                val unitID = propertyUnit.id
                val unitRef = db.collection("units").document(propertyUnit.id)
                val dealRef = deal?.let {
                    if (it.id.isEmpty()) {
                        db.collection("deals").document()
                    } else {
                        db.collection("deals").document(it.id)
                    }
                }
                val paymentRef = payment?.let {
                    if (it.id.isEmpty()) {
                        db.collection("payments").document()
                    } else {
                        db.collection("payments").document(it.id)
                    }
                }
                val adminRef = db.collection("admin").document("adminDetails")
                db.runTransaction { transaction ->
                    if (dealRef != null && paymentRef != null) {
                        transaction.set(
                            dealRef, deal.copy(
                                id = dealRef.id, unitId = unitID, payments = listOf(paymentRef.id)
                            )
                        )
                        transaction.set(
                            paymentRef,
                            payment.copy(id = paymentRef.id, dealId = dealRef.id, unitId = unitID)
                        )
                        transaction.set(
                            unitRef, propertyUnit.copy(deals = listOf(dealRef.id))
                        )

                    } else {
                        transaction.set(
                            unitRef, propertyUnit.copy(deals = emptyList(), id = unitID)
                        )
                    }

                    if (deal != null) {
                        transaction.update(adminRef, "totalDeals", FieldValue.increment(1))
                        transaction.update(
                            adminRef, "totalSales", FieldValue.increment(deal.totalPrice)
                        )
                    }
                }.await()
            } catch (e: Exception) {
                emit(Resource.Error("Error updating unit"))
            }
        }
    }
}

fun DocumentSnapshot?.toPropertyUnit(): PropertyUnit {
    return this?.let {
        PropertyUnit(
            id = it.id,
            name = it.get("name") as String,
            unitCode = it.get("unitCode") as String,
            developmentId = it.get("developmentId") as String,
            developmentName = it.get("developmentName") as String,
            buildingId = it.get("buildingId") as String,
            buildingName = it.get("buildingName") as String,
            unitType = it.get("unitType") as String,
            price = it.get("price") as Double,
            status = it.get("status") as String,
            createdTime = it.get("createdTime") as String,
            deals = (it.get("deals") as? List<String>) ?: emptyList()
        )
    } ?: throw IllegalArgumentException("DocumentReference is null")
}