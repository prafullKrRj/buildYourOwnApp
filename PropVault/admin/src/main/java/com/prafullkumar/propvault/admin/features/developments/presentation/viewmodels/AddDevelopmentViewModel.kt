package com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.prafullkumar.common.models.Development
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class AddDevelopmentViewModel(

) : ViewModel(), KoinComponent {

    private val repository by inject<DevelopmentsRepository>()

    var name by mutableStateOf("")
        private set
    var category by mutableStateOf("")
        private set
    var website by mutableStateOf("")
        private set
    var city by mutableStateOf("")
        private set
    var attorneyFirmName by mutableStateOf("")
        private set
    var brokerageFeeCommission by mutableStateOf("")
        private set
    var status by mutableStateOf("")
        private set
    var startingPrice by mutableStateOf("")
        private set
    var numberOfBuildings by mutableStateOf("")
        private set
    var imageUrl by mutableStateOf("")
        private set

    // Status indicators
    var isSaving by mutableStateOf(false)
        private set
    var isSuccess by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    val categoryOptions = listOf("Residential", "Commercial", "Mixed-Use", "Industrial")
    val statusOptions = listOf("Pre-Construction", "Under Construction", "Completed", "Sold Out")


    fun updateName(input: String) {
        name = input
    }

    fun updateCategory(input: String) {
        category = input
    }

    fun updateWebsite(input: String) {
        website = input
    }

    fun updateCity(input: String) {
        city = input
    }

    fun updateAttorneyFirmName(input: String) {
        attorneyFirmName = input
    }

    fun updateBrokerageFeeCommission(input: String) {
        brokerageFeeCommission = input
    }

    fun updateStatus(input: String) {
        status = input
    }

    fun updateStartingPrice(input: String) {
        startingPrice = input
    }

    fun updateNumberOfBuildings(input: String) {
        numberOfBuildings = input
    }

    fun updateImageUrl(input: String) {
        imageUrl = input
    }

    fun clearFields() {
        name = ""
        category = ""
        website = ""
        city = ""
        attorneyFirmName = ""
        brokerageFeeCommission = ""
        status = ""
        startingPrice = ""
        numberOfBuildings = ""
        imageUrl = ""
    }

    // Validation function
    fun validateInputs(): Boolean {
        if (name.isBlank() || category.isBlank() || city.isBlank() || status.isBlank()) {
            errorMessage = "Please fill in all required fields"
            return false
        }

        try {
            if (brokerageFeeCommission.isNotBlank()) {
                brokerageFeeCommission.toInt()
            }
            if (startingPrice.isNotBlank()) {
                startingPrice.toDouble()
            }
            if (numberOfBuildings.isNotBlank()) {
                numberOfBuildings.toInt()
            }
        } catch (e: NumberFormatException) {
            errorMessage = "Please enter valid numbers for numeric fields"
            return false
        }

        return true
    }

    // Save development function
    suspend fun saveDevelopment() {
        if (!validateInputs()) return

        try {
            isSaving = true
            errorMessage = null

            val development = Development(
                name = name,
                category = category,
                website = website,
                city = city,
                attorneyFirmName = attorneyFirmName,
                brokerageFeeCommission = brokerageFeeCommission.toIntOrNull() ?: 0,
                status = status,
                startingPrice = startingPrice.toDoubleOrNull() ?: 0.0,
                numberOfBuildings = numberOfBuildings.toIntOrNull() ?: 0,
                imageUrl = imageUrl.ifBlank { null },
                buildings = emptyList(),
                id = ""
            )

            repository.addDevelopment(development).collectLatest {
                when (it) {
                    is Resource.Error -> {
                        isSuccess = false
                    }

                    Resource.Loading -> {
                        isSaving = true
                    }

                    is Resource.Success<*> -> {
                        isSuccess = true
                        clearFields()
                    }
                }
            }

        } catch (e: Exception) {
            errorMessage = "Error saving development: ${e.message}"
        } finally {
            isSaving = false
        }
    }
}
