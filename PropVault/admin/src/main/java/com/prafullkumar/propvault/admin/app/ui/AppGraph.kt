package com.prafullkumar.propvault.admin.app.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.prafullkumar.common.presentation.navigation.MainRoutes
import com.prafullkumar.propvault.admin.app.navigation.AppRoutes
import com.prafullkumar.propvault.admin.features.deals.DealScreen


import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


fun NavGraphBuilder.appGraph(
    navController: NavController
) {
    navigation<MainRoutes.App>(startDestination = AppRoutes.Home) {
        composable<AppRoutes.Home> {
            MainScreen(
                selectedItem = AppRoutes.Home, onItemSelected = {
                    navController.navigate(it)
                }, navController
            )
        }
        composable<AppRoutes.Developments> {
            MainScreen(
                selectedItem = AppRoutes.Developments, onItemSelected = {
                    navController.navigate(it)
                }, navController

            )
        }

        composable<AppRoutes.Search> {
            MainScreen(
                selectedItem = AppRoutes.Search, onItemSelected = {
                    navController.navigate(it)
                }, navController
            )
        }
        composable<AppRoutes.Deals> {
            MainScreen(
                selectedItem = AppRoutes.Deals, onItemSelected = {
                    navController.navigate(it)
                }, navController
            )
        }
        composable<AppRoutes.DealScreen> {
            DealScreen(
                koinViewModel { parametersOf(it.toRoute<AppRoutes.DealScreen>().dealId) },
                navController
            )
        }
    }
}