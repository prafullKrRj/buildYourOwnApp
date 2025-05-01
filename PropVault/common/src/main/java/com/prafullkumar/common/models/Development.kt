package com.prafullkumar.common.models


data class Development (
    val id: String,
    val name: String,
    val category: String,
    val website: String,
    val city: String,
    val attorneyFirmName: String,
    val brokerageFeeCommission: Int,
    val status: String,
    val startingPrice: Double,
    val numberOfBuildings: Int,
    val imageUrl: String? = null,
    val buildings: List<String> = emptyList(),
)