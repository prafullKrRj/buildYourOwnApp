package com.prafullkumar.common.models

import java.util.UUID


data class Payment(
    val id: String,
    val dealId: String,
    val unitId: String = UUID.randomUUID().toString(),
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