package com.prafullkumar.propvault.admin.features.home.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.deals.data.toDeal
import com.prafullkumar.propvault.admin.features.deals.data.toPayment
import com.prafullkumar.propvault.admin.features.home.domain.model.AdminDetails
import com.prafullkumar.propvault.admin.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl : HomeRepository {
    private val db = FirebaseFirestore.getInstance()
    override fun getAdminDetails(): Flow<Resource<AdminDetails>> {
        return flow {
            try {
                val response = db.collection("admin").document("adminDetails")
                    .get().await().toAdminDetails()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }

    override fun getRecentDeals(): Flow<Resource<List<Deal>>> {
        return flow {
            try {
                val response = db.collection("deals")
                    .orderBy("dealDate", Query.Direction.DESCENDING)
                    .limit(4)
                    .get().await()
                val deals = response.documents.mapNotNull { doc ->
                    doc.toDeal()
                }
                emit(Resource.Success(deals))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }

    override fun getRecentPayments(): Flow<Resource<List<Payment>>> {
        return flow {
            try {
                val response = db.collection("payments")
                    .orderBy("paymentDate", Query.Direction.DESCENDING)
                    .limit(4)
                    .get().await()
                val payments = response.documents.mapNotNull { doc ->
                    doc.toPayment()
                }
                emit(Resource.Success(payments))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }
}

fun DocumentSnapshot?.toAdminDetails(): AdminDetails {
    return this?.let {
        AdminDetails(
            totalDevelopments = it.getLong("totalDevelopments")?.toInt() ?: 0,
            totalDeals = it.getLong("totalDeals")?.toInt() ?: 0,
            totalSales = it.getDouble("totalSales") ?: 0.0,
        )
    } ?: throw IllegalArgumentException("DocumentSnapshot is null")
}