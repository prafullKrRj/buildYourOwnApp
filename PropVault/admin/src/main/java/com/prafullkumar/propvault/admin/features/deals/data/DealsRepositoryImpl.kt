package com.prafullkumar.propvault.admin.features.deals.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.DealStatus
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PaymentStatus
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.core.model.DealWithPayments
import com.prafullkumar.propvault.admin.features.deals.domain.repo.DealsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent

class DealsRepositoryImpl : DealsRepository, KoinComponent {
    private val db = FirebaseFirestore.getInstance()
    override fun getAllDeals(): Flow<Resource<List<Deal>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = db.collection("deals").get().await()
                val deals = response.documents.mapNotNull { deal ->
                    deal.toDeal()
                }
                emit(Resource.Success(deals))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }

    override fun getDealWithPayments(dealId: String): Flow<Resource<DealWithPayments>> {
        return flow {
            try {
                val deal = db.collection("deals").document(dealId).get().await().toDeal()
                if (deal == null) {
                    emit(Resource.Error("Deal not found"))
                    return@flow
                }
                val payments = deal.payments.map {
                    db.collection("payments").document(it).get().await().toPayment()
                }
                emit(Resource.Success(DealWithPayments(deal, payments)))
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }


}

fun DocumentSnapshot?.toPayment(): Payment {
    return this?.let {
        Payment(
            id = it.id,
            dealId = it.getString("dealId") ?: return@let null,
            amount = it.getDouble("amount") ?: return@let null,
            dueAmount = it.getDouble("dueAmount") ?: return@let null,
            paymentDate = it.getString("paymentDate") ?: return@let null,
            totalPrice = it.getDouble("totalPrice") ?: return@let null,
            depositedAmount = it.getDouble("depositedAmount") ?: return@let null,
            status = PaymentStatus.valueOf(it.getString("status") ?: return@let null),
            remarks = it.getString("remarks"),
            unitId = it.getString("unitId") ?: return@let null,
        )
    } ?: throw IllegalArgumentException("DocumentSnapshot is null")
}

fun DocumentSnapshot?.toDeal(): Deal? {
    return this?.let {
        Deal(
            id = it.id,
            unitId = it.getString("unitId") ?: return@let null,
            customerName = it.getString("customerName") ?: return@let null,
            customerPhone = it.getString("customerPhone") ?: return@let null,
            customerEmail = it.getString("customerEmail") ?: return@let null,
            totalPrice = it.getDouble("totalPrice") ?: return@let null,
            dealDate = it.getString("dealDate") ?: return@let null,
            closingDate = it.getString("closingDate"),
            assignedAgent = it.getString("assignedAgent") ?: return@let null,
            status = DealStatus.valueOf(it.getString("status") ?: return@let null),
            remarks = it.getString("remarks"),
            payments = it.get("payments") as? List<String> ?: emptyList()
        )
    }
}