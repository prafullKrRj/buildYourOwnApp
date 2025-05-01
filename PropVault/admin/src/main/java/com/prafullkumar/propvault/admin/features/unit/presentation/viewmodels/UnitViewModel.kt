package com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.unit.domain.UnitRepository
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UnitViewModel(
    private val unitId: String,
) : ViewModel(), KoinComponent {

    private val repository by inject<UnitRepository>()

    private val _unitWithDetails =
        MutableStateFlow<Resource<UnitWithDealsAndPayments>>(Resource.Loading)


    val unitWithDetails = _unitWithDetails.asStateFlow()

    init {
        getUnitDetails()
    }

    fun getUnitDetails() {
        viewModelScope.launch {
            repository.getUnitDetails(unitId).collectLatest { response ->
                _unitWithDetails.update { response }
            }
        }
    }

}