package com.prafullkumar.propvault.admin.features.unit.presentation.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels.UnitEditViewModel

@Composable
fun UnitEditingScreen(
    viewModel: UnitEditViewModel, navController: NavController
) {
    val state by viewModel.unitWithDetails.collectAsState()
    LaunchedEffect(Unit) {
        Log.d("UnitEditingScreen", "UnitDetails: $state")
    }
    when (state) {
        is Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Error -> {
            ErrorScreen()
        }

        is Resource.Success -> {
            val unitDetails = (state as Resource.Success<UnitWithDealsAndPayments>).data
            UnitManagementScreen(
                navController = navController,
                unitId = unitDetails.unit.id,
                developmentId = unitDetails.unit.developmentId,
                developmentName = unitDetails.unit.developmentName,
                buildingId = unitDetails.unit.buildingId,
                buildingName = unitDetails.unit.buildingName,
                viewModel = viewModel,
                unitDetails
            )
        }
    }

}
