package com.prafullkumar.propvault.admin.core.domain.model


data class Building(
    val id: Int,
    val name: String,
    val developmentId: Int,
    val developmentName: String,
    val unitsCount: Int,
    val lastUpdateTime: String? = null,
    val units: List<Int> = emptyList(),
//    val openActivities: List<Activity> = emptyList(),
//    val closedActivities: List<Activity> = emptyList()
)