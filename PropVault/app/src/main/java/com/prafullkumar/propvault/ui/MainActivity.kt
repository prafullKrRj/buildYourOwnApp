package com.prafullkumar.propvault.ui

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.common.presentation.navigation.MainRoutes
import com.prafullkumar.propvault.onBoarding.ui.OnBoardingViewModel
import com.prafullkumar.propvault.onBoarding.ui.RealEstateLoginScreen
import com.prafullkumar.propvault.ui.theme.PropVaultTheme
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PropVaultTheme {
                val navController = rememberNavController()
                val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
                    MainRoutes.App
                } else {
                    MainRoutes.OnBoarding
                }
                NavGraph(
                    navController = navController,
                    startDestination = startDestination,
                    updateLoginStatus = { isLoggedIn ->
                        if (isLoggedIn) {
                            navController.navigate(MainRoutes.App) {
                                popUpTo(MainRoutes.OnBoarding) {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.navigate(MainRoutes.OnBoarding) {
                                popUpTo(MainRoutes.App) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Any,
    updateLoginStatus: (Boolean) -> Unit = {},
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        onBoardingGraph(navController)
        composable<MainRoutes.App> {
            Box(Modifier.fillMaxSize()) {
                Text("Welcome to the App")
            }
        }
    }
}

fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}

sealed interface OnBoardingRoutes : Parcelable {

    @Parcelize
    @Serializable
    data object Login : OnBoardingRoutes
}

fun NavGraphBuilder.onBoardingGraph(
    navController: NavHostController,
) {
    navigation<MainRoutes.OnBoarding>(startDestination = OnBoardingRoutes.Login) {
        composable<OnBoardingRoutes.Login> {
            RealEstateLoginScreen(
                koinViewModel<OnBoardingViewModel>(),
                navController = navController
            )
        }
    }
}