package com.prafullkumar.propvault.customer.app.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface CustomerRoutes {


    @Serializable
    data object Home : CustomerRoutes

    @Serializable
    data class PropertyDetailsScreen(
        val propertyId: String
    ) : CustomerRoutes

}