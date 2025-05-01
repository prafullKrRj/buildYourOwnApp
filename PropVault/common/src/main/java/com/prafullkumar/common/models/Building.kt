package com.prafullkumar.common.models


data class Building(
    val id: String,
    val name: String,
    val developmentId: String,
    val developmentName: String,
    val unitsCount: Int,
    val lastUpdateTime: String? = null,
    val units: List<String> = emptyList(),
)