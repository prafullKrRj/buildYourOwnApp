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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.presentation.PropertyUnitListScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.BuildingUnitViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuildingUnitScreen(
    viewModel: BuildingUnitViewModel, navController: NavController, data: AdminRoutes.Units
) {
    val units by viewModel.units.collectAsState()
    when (units) {
        is Resource.Error -> {
            ErrorScreen()
        }

        Resource.Loading -> LoadingScreen()
        is Resource.Success<*> -> {
            BuildingUnitSuccessScreen(
                units = (units as Resource.Success<List<PropertyUnit>>).data,
                navController = navController,
                data = data
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuildingUnitSuccessScreen(
    units: List<PropertyUnit>, navController: NavController, data: AdminRoutes.Units
) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Building Units")
        }, navigationIcon = {
            IconButton(onClick = navController::popBackStack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            navController.navigate(
                AdminRoutes.AddUnit(
                    developmentName = data.developmentName,
                    buildingName = data.buildingName,
                    buildingId = data.buildingId,
                    developmentId = data.developmentId
                )
            )
        }) {
            Icon(
                Icons.Default.Add, contentDescription = "Add Unit"
            )
        }
    }) { paddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PropertyUnitListScreen(units = units) {
                navController.navigate(
                    AdminRoutes.UnitScreen(it.id)
                )
            }
        }
    }
}