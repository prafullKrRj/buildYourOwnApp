package com.prafullkumar.propvault.admin.core.domain.model


data class Deal(
    val id: Int,
    val unitId: Int,
    val customerName: String,
    val customerPhone: String,
    val customerEmail: String,
    val totalPrice: Double,
    val dealDate: String,
    val closingDate: String?,
    val assignedAgent: String,
    val status: DealStatus,
    val remarks: String?
)
{

}