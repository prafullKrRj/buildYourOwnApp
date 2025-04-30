package com.prafullkumar.propvault.admin.app.navigation

import kotlinx.serialization.Serializable


sealed interface AppRoutes {
    @Serializable
    data object Home : AppRoutes

    @Serializable
    data object Developments : AppRoutes

    @Serializable
    data object Search : AppRoutes

    @Serializable
    data object Deals : AppRoutes

    @Serializable
    data class DealScreen(
        val dealId: Int,
    ) : AppRoutes

    @Serializable
    data class DevelopmentBuildingsScreen(
        val developmentId: Int,
        val developmentName: String,
    ) : AppRoutes

    @Serializable
    data class Units(
        val buildingId: Int,
        val buildingName: String,
        val developmentId: Int,
        val developmentName: String,
    ) : AppRoutes

    @Serializable
    data object AddDevelopment : AppRoutes

    @Serializable
    data object PaymentsScreen : AppRoutes

    @Serializable
    data class AddBuilding(
        val developmentId: Int,
        val developmentName: String,
    ) : AppRoutes

    @Serializable
    data class EditUnit(val unitId: Int) : AppRoutes

    @Serializable
    data class AddUnit(
        val buildingId: Int,
        val buildingName: String,
        val developmentId: Int,
        val developmentName: String,
    ) : AppRoutes

    @Serializable
    data class UnitScreen(
        val unitId: Int
    ) : AppRoutes
}