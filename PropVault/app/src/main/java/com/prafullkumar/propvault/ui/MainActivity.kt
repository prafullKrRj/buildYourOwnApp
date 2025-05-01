package com.prafullkumar.propvault.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.common.navigation.MainRoutes
import com.prafullkumar.propvault.admin.app.ui.adminGraph
import com.prafullkumar.propvault.customer.app.presentation.navigation.customerGraph
import com.prafullkumar.propvault.onBoarding.ui.onBoardingGraph
import com.prafullkumar.propvault.ui.theme.PropVaultTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PropVaultTheme {
                val navController = rememberNavController()
                val startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
                    if (FirebaseAuth.getInstance().currentUser?.email?.contains("admin")!!) {
                        MainRoutes.Admin
                    } else {
                        MainRoutes.Customer
                    }
                } else {
                    MainRoutes.OnBoarding
                }
                NavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: Any,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        onBoardingGraph(navController)
        adminGraph(navController)
        customerGraph(navController)
    }
}