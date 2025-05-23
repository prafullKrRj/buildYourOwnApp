package com.prafullkumar.propvault.admin.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.deals.presentation.screens.DealsScreen
import com.prafullkumar.propvault.admin.features.developments.presentation.screens.DevelopmentsScreen
import com.prafullkumar.propvault.admin.features.home.presentation.screens.HomeScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun MainScreen(
    selectedItem: Any,
    onItemSelected: (Any) -> Unit,
    navController: NavController
) {
    Scaffold(
        Modifier.fillMaxSize(), bottomBar = {
            NavigationBar(Modifier.fillMaxWidth()) {
                BottomNavigationItems.entries.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = if (item.route == selectedItem) item.selectedIcon else item.unselectedIcon,
                                contentDescription = null
                            )
                        },
                        label = { Text(item.title) },
                        selected = item.route == selectedItem,
                        onClick = { onItemSelected(item.route) })
                }
            }
        }) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            when (selectedItem) {
                AdminRoutes.Home -> {
                    HomeScreen(
                        viewModel = koinViewModel(),
                        navController = navController
                    )
                }

                AdminRoutes.Developments -> {
                    DevelopmentsScreen(
                        viewModel = koinViewModel(),
                        navController = navController
                    )
                }

                AdminRoutes.Deals -> {
                    DealsScreen(koinViewModel(), navController)
                }
            }
        }
    }
}


enum class BottomNavigationItems(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val title: String,
    val route: Any
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = "Home",
        route = AdminRoutes.Home
    ),
    DEVELOPMENTS(
        selectedIcon = Icons.Filled.Apartment,
        unselectedIcon = Icons.Outlined.Apartment,
        title = "Developments",
        route = AdminRoutes.Developments
    ),

    DEALS(
        selectedIcon = Icons.Filled.LocalOffer,
        unselectedIcon = Icons.Outlined.LocalOffer,
        title = "Deals",
        route = AdminRoutes.Deals
    )
}