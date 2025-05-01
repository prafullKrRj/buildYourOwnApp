package com.prafullkumar.propvault.onBoarding.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prafullkumar.common.navigation.MainRoutes
import com.prafullkumar.propvault.onBoarding.ui.navigation.OnBoardingRoutes
import com.prafullkumar.propvault.onBoarding.ui.viewmodel.OnBoardingViewModel
import com.prafullkumar.propvault.onBoarding.ui.screens.RealEstateLoginScreen
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.onBoardingGraph(
    navController: NavHostController,
) {
    navigation<MainRoutes.OnBoarding>(startDestination = OnBoardingRoutes.Login) {
        composable<OnBoardingRoutes.Login> {
            RealEstateLoginScreen(
                koinViewModel<OnBoardingViewModel>(), navController = navController
            )
        }
    }
}