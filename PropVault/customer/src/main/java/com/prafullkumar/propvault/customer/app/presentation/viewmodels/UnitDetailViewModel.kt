package com.prafullkumar.propvault.customer.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.sampleData.propertyUnits
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.customer.app.domain.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class UnitDetailViewModel(
    private val propertyId: String
) : ViewModel(), KoinComponent {

    private val repository by inject<CustomerRepository>()

    private val _property = MutableStateFlow<Resource<PropertyUnit>>(Resource.Loading)
    val property = _property.asStateFlow()
    init {
        getProperties()
    }
    fun getProperties() {
       viewModelScope.launch {
           repository.getProperty(propertyId).collectLatest { response ->
               _property.update {
                   response
               }
           }
       }
    }
}
