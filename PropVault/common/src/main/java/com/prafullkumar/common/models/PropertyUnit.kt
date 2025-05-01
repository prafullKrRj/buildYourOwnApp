package com.prafullkumar.common.models


data class PropertyUnit(
    val id: String,
    val name: String,
    val unitCode: String,
    val developmentId: String,
    val developmentName: String,
    val buildingId: String,
    val buildingName: String,
    val unitType: String,
    val price: Double,
    val status: String,
    val createdTime: String,
    val deals: List<String> = emptyList()
)