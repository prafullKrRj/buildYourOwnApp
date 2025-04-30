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

        composable<AppRoutes.Units> {
//            BuildingUnitScreen(
//                koinViewModel { parametersOf(it.toRoute<AppRoutes.Units>().buildingId) },
//                navController,
//                it.toRoute<AppRoutes.Units>()
//            )
        }
        composable<AppRoutes.DevelopmentBuildingsScreen> {
//            val development = it.toRoute<AppRoutes.DevelopmentBuildingsScreen>()
//            DevelopmentBuildingScreen(
//                koinViewModel {
//                    parametersOf(
//                        development.developmentId, development.developmentName
//                    )
//                }, navController
//            )
        }
        composable<AppRoutes.DealScreen> {
            DealScreen(
                koinViewModel { parametersOf(it.toRoute<AppRoutes.DealScreen>().dealId) },
                navController
            )
        }
        composable<AppRoutes.AddDevelopment> {
//            AddDevelopmentScreen(
//                koinViewModel(), navController
//            )
        }
        composable<AppRoutes.AddBuilding> {
//            val details = it.toRoute<AppRoutes.AddBuilding>()
//            AddBuildingScreen(koinViewModel {
//                parametersOf(
//                    details.developmentId, details.developmentName
//                )
//            }, navController)
        }
        composable<AppRoutes.EditUnit> {
//            val unitDetails = it.toRoute<AppRoutes.EditUnit>()
//            UnitEditingScreen(koinViewModel {
//                parametersOf(
//                    unitDetails.unitId
//                )
//            }, navController)
        }
        composable<AppRoutes.AddUnit> {
//            AddUnitScreen(
//                koinViewModel { parametersOf(0) }, navController, it.toRoute<AppRoutes.AddUnit>()
//            )
        }

        composable<AppRoutes.UnitScreen> {
//            UnitScreen(
//                navController = navController,
//                viewModel = koinViewModel { parametersOf(it.toRoute<AppRoutes.UnitScreen>().unitId) })
        }
        composable<AppRoutes.PaymentsScreen> {
//            PaymentScreen(
//                koinViewModel(), navController
//            )
        }
    }
}