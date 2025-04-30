package com.prafullkumar.propvault.admin.core.domain.model



data class Payment(
    val id: Int,
    val dealId: Int,
    val amount: Double,
    val totalPrice: Double,
    val depositedAmount: Double,
    val dueAmount: Double,
    val paymentDate: String,
    val status: PaymentStatus,
    val remarks: String?
)

enum class DealStatus {
    PENDING,
    ACTIVE,
    COMPLETED,
    CANCELLED
}

enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED
}