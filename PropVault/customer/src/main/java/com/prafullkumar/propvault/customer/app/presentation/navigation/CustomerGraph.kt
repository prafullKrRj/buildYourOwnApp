package com.prafullkumar.propvault.customer.app.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.prafullkumar.common.navigation.MainRoutes
import com.prafullkumar.propvault.customer.app.presentation.screens.PropertyListScreen
import com.prafullkumar.propvault.customer.app.presentation.screens.PropertyDetailScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.customerGraph(navController: NavController) {
    navigation<MainRoutes.Customer>(startDestination = CustomerRoutes.Home) {
        composable<CustomerRoutes.Home> {
            PropertyListScreen(koinViewModel(), navController)
        }

        composable<CustomerRoutes.PropertyDetailsScreen> {
            val propertyId = it.toRoute<CustomerRoutes.PropertyDetailsScreen>().propertyId
            PropertyDetailScreen(koinViewModel { parametersOf(propertyId) }, navController)
        }
    }
}