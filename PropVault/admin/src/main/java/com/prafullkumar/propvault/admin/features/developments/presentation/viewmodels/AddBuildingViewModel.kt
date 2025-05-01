package com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.Building
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Date


class AddBuildingViewModel(
    val developmentId: String,
    val developmentName: String,
) : ViewModel(), KoinComponent {

    private val repository by inject<DevelopmentsRepository>()

    // State holders for all fields
    var buildingName by mutableStateOf("")
        private set
    var unitsCount by mutableStateOf("")
        private set

    // Status indicators
    var isSaving by mutableStateOf(false)
        private set
    var isSuccess by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    // Update functions for each field
    fun updateBuildingName(input: String) {
        buildingName = input
    }

    fun updateUnitsCount(input: String) {
        unitsCount = input
    }

    // Clear all fields
    fun clearFields() {
        buildingName = ""
        unitsCount = ""
    }

    // Validation function
    fun validateInputs(): Boolean {
        if (buildingName.isBlank()) {
            errorMessage = "Building name is required"
            return false
        }

        try {
            if (unitsCount.isNotBlank()) {
                unitsCount.toInt()
            }
        } catch (e: NumberFormatException) {
            errorMessage = "Please enter a valid number for units count"
            return false
        }

        return true
    }

    fun saveBuilding() {
        if (!validateInputs()) {
            return
        }
        isSaving = true
        viewModelScope.launch {
            repository.addBuilding(
                building = Building(
                    id = "",
                    name = buildingName,
                    developmentId = developmentId,
                    developmentName = developmentName,
                    unitsCount = 0,
                    lastUpdateTime = Date().time.toString()
                )
            ).collectLatest {
                when (it) {
                    is Resource.Error -> {
                        isSaving = false
                        errorMessage = it.message
                    }

                    Resource.Loading -> {
                        isSaving = true
                    }

                    is Resource.Success<*> -> {
                        isSaving = false
                        isSuccess = it.data as Boolean
                        if (isSuccess) {
                            clearFields()
                        } else {
                            errorMessage = "Failed to save building"
                        }
                    }
                }
            }
        }
    }
}