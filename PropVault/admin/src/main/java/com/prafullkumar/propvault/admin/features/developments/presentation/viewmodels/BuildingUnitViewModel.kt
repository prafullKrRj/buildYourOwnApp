package com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class BuildingUnitViewModel(
    val buildingId: String,
) : ViewModel(), KoinComponent {

    private val repository by inject<DevelopmentsRepository>()
    private val _units = MutableStateFlow<Resource<List<PropertyUnit>>>(Resource.Loading)
    val units = _units.asStateFlow()

    init {
        getUnits()
    }

    fun getUnits() {
        viewModelScope.launch {
            repository.getUnits(buildingId).collect { result ->
                _units.value = result
            }
        }
    }

}

