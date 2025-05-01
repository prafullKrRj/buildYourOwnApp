package com.prafullkumar.propvault.customer.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.sampleData.PropertyStatus
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

class PropertyListViewModel : ViewModel(), KoinComponent {

    private val repository by inject<CustomerRepository>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val _properties = MutableStateFlow<Resource<List<PropertyUnit>>>(Resource.Loading)
    val properties = _properties.asStateFlow()
    fun signOut() {
        firebaseAuth.signOut()
    }

    init {
        getProperties()
    }
    fun getProperties() {
        viewModelScope.launch {
            repository.getProperties().collectLatest {  response ->
                _properties.update { response }
            }
        }
    }
}
