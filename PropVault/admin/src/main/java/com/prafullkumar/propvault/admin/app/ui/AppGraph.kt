package com.prafullkumar.propvault.admin.app.ui


import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.prafullkumar.common.navigation.MainRoutes
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.deals.presentation.screens.DealScreen
import com.prafullkumar.propvault.admin.features.developments.presentation.screens.AddBuildingScreen
import com.prafullkumar.propvault.admin.features.developments.presentation.screens.AddDevelopmentScreen
import com.prafullkumar.propvault.admin.features.developments.presentation.screens.BuildingUnitScreen
import com.prafullkumar.propvault.admin.features.developments.presentation.screens.BuildingsScreen
import com.prafullkumar.propvault.admin.features.home.presentation.screens.PaymentScreen
import com.prafullkumar.propvault.admin.features.unit.presentation.screens.AddUnitScreen
import com.prafullkumar.propvault.admin.features.unit.presentation.screens.UnitEditingScreen
import com.prafullkumar.propvault.admin.features.unit.presentation.screens.UnitScreen

import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf


fun NavGraphBuilder.adminGraph(
    navController: NavController
) {
    navigation<MainRoutes.Admin>(startDestination = AdminRoutes.Home) {
        composable<AdminRoutes.Home> {
            MainScreen(
                selectedItem = AdminRoutes.Home, onItemSelected = {
                    navController.navigate(it)
                }, navController
            )
        }
        composable<AdminRoutes.Developments> {
            MainScreen(
                selectedItem = AdminRoutes.Developments, onItemSelected = {
                    navController.navigate(it)
                }, navController

            )
        }


        composable<AdminRoutes.Deals> {
            MainScreen(
                selectedItem = AdminRoutes.Deals, onItemSelected = {
                    navController.navigate(it)
                }, navController
            )
        }
        composable<AdminRoutes.DealScreen> {
            DealScreen(
                koinViewModel { parametersOf(it.toRoute<AdminRoutes.DealScreen>().dealId) },
                navController
            )
        }
        composable<AdminRoutes.AddDevelopment> {

            AddDevelopmentScreen(
                koinViewModel(), navController
            )
        }

        composable<AdminRoutes.AddBuilding> {
            val details = it.toRoute<AdminRoutes.AddBuilding>()
            AddBuildingScreen(koinViewModel {
                parametersOf(
                    details.developmentId, details.developmentName
                )
            }, navController)
        }
        composable<AdminRoutes.Units> {
            BuildingUnitScreen(
                koinViewModel { parametersOf(it.toRoute<AdminRoutes.Units>().buildingId) },
                navController,
                it.toRoute<AdminRoutes.Units>()
            )
        }
        composable<AdminRoutes.DevelopmentBuildingsScreen> {
            val development = it.toRoute<AdminRoutes.DevelopmentBuildingsScreen>()
            BuildingsScreen(
                koinViewModel {
                    parametersOf(
                        development.developmentId, development.developmentName
                    )
                }, navController
            )
        }

        composable<AdminRoutes.PaymentsScreen> {
            PaymentScreen(
                koinViewModel(), navController
            )
        }
        composable<AdminRoutes.EditUnit> {
            val unitDetails = it.toRoute<AdminRoutes.EditUnit>()
            UnitEditingScreen(koinViewModel {
                parametersOf(
                    unitDetails.unitId
                )
            }, navController)
        }
        composable<AdminRoutes.AddUnit> {
            AddUnitScreen(
                koinViewModel { parametersOf("") }, navController, it.toRoute<AdminRoutes.AddUnit>()
            )
        }

        composable<AdminRoutes.UnitScreen> {
            UnitScreen(
                navController = navController,
                viewModel = koinViewModel { parametersOf(it.toRoute<AdminRoutes.UnitScreen>().unitId) })
        }
    }
}