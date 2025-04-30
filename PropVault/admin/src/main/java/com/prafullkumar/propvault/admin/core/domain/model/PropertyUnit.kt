package com.prafullkumar.propvault.admin.core.domain.model


data class PropertyUnit(
    val id: Int,
    val name: String,
    val unitCode: String,
    val developmentId: Int,
    val developmentName: String,
    val buildingId: Int,
    val buildingName: String,
    val unitType: String,
    val price: Double,
    val status: String,
    val createdTime: String,
)