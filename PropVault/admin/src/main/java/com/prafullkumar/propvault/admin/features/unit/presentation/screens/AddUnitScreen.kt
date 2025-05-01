package com.prafullkumar.propvault.admin.features.unit.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels.UnitEditViewModel

@Composable
fun AddUnitScreen(
    viewModel: UnitEditViewModel, navController: NavController,
    data: AdminRoutes.AddUnit
) {
    val unitState by viewModel.unitWithDetails.collectAsState()
    when (unitState) {
        is Resource.Error -> {
            ErrorScreen()
        }

        Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Success<*> -> {
            val unitDetails = (unitState as Resource.Success<UnitWithDealsAndPayments>).data
            UnitManagementScreen(
                navController = navController,
                unitId = unitDetails.unit.id,
                developmentId = data.developmentId,
                developmentName = data.developmentName,
                buildingId = data.buildingId,
                buildingName = data.buildingName,
                viewModel = viewModel,
                unitDetails
            )
        }
    }

}
