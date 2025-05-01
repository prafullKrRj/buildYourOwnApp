package com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.Building
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DevelopmentBuildingsViewModel(
    val developmentId: String,
    val developmentName: String,
) : ViewModel(), KoinComponent {

    private val repository by inject<DevelopmentsRepository>()
    private val _buildings = MutableStateFlow<Resource<List<Building>>>(Resource.Loading)
    val buildings = _buildings.asStateFlow()

    init {
        getBuildings()
    }

    fun getBuildings() {
        viewModelScope.launch {
            Log.d("GetBuildings", "Development ID: $developmentId")
            repository.getBuildings(developmentId).collectLatest { response ->
                _buildings.update {
                    response
                }
            }
        }
    }
}