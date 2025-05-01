package com.prafullkumar.propvault.admin.features.developments.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.prafullkumar.common.models.Building
import com.prafullkumar.common.presentation.BuildingsList
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.DevelopmentBuildingsViewModel


@Composable
fun BuildingsScreen(
    viewModel: DevelopmentBuildingsViewModel, navController: NavController
) {
    val buildings by viewModel.buildings.collectAsState()
    when (buildings) {
        is Resource.Error -> {
            ErrorScreen(onRetry = {
                viewModel.getBuildings()
            })
        }

        Resource.Loading -> LoadingScreen()
        is Resource.Success<*> -> {
            BuildingsScreenSuccess(
                viewModel,
                navController,
                (buildings as Resource.Success<List<Building>>).data
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuildingsScreenSuccess(
    viewModel: DevelopmentBuildingsViewModel, navController: NavController,
    buildings: List<Building>
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = viewModel.developmentName, style = MaterialTheme.typography.headlineSmall
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                AdminRoutes.AddBuilding(
                    developmentId = viewModel.developmentId,
                    developmentName = viewModel.developmentName
                )
            )
        }) {
            Icon(
                Icons.Default.Add, contentDescription = "Add Building Button"
            )
        }
    }) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BuildingsList(
                buildings = buildings, onBuildingClick = {
                    navController.navigate(
                        AdminRoutes.Units(
                            it.id, it.name, viewModel.developmentId, viewModel.developmentName
                        )
                    )
                })
        }
    }
}