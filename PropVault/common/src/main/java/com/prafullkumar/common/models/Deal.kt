package com.prafullkumar.common.models

import java.util.UUID


data class Deal(
    val id: String,
    val unitId: String,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val totalPrice: Double,
    val dealDate: String,
    val closingDate: String?,
    val assignedAgent: String,
    val status: DealStatus,
    val remarks: String?,
    val payments: List<String> = emptyList()
)

fun getDefaultDeal(): Deal {
    return Deal(
        id = UUID.randomUUID().toString(),
        unitId = UUID.randomUUID().toString(),
        customerName = "",
        customerPhone = "",
        customerEmail = "",
        totalPrice = 0.0,
        dealDate = "",
        closingDate = null,
        assignedAgent = "",
        status = DealStatus.PENDING,
        remarks = null
    )
}