package com.prafullkumar.propvault.admin.app.navigation

import kotlinx.serialization.Serializable


sealed interface AdminRoutes {
    @Serializable
    data object Home : AdminRoutes

    @Serializable
    data object Developments : AdminRoutes


    @Serializable
    data object Deals : AdminRoutes

    @Serializable
    data class DealScreen(
        val dealId: String,
    ) : AdminRoutes

    @Serializable
    data class DevelopmentBuildingsScreen(
        val developmentId: String,
        val developmentName: String,
    ) : AdminRoutes

    @Serializable
    data class Units(
        val buildingId: String,
        val buildingName: String,
        val developmentId: String,
        val developmentName: String,
    ) : AdminRoutes

    @Serializable
    data object AddDevelopment : AdminRoutes

    @Serializable
    data object PaymentsScreen : AdminRoutes

    @Serializable
    data class AddBuilding(
        val developmentId: String,
        val developmentName: String,
    ) : AdminRoutes

    @Serializable
    data class EditUnit(val unitId: String) : AdminRoutes

    @Serializable
    data class AddUnit(
        val buildingId: String,
        val buildingName: String,
        val developmentId: String,
        val developmentName: String,
    ) : AdminRoutes

    @Serializable
    data class UnitScreen(
        val unitId: String
    ) : AdminRoutes
}