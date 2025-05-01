package com.prafullkumar.propvault.admin.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.home.domain.model.AdminDetails
import com.prafullkumar.propvault.admin.features.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class HomeViewModel(

) : ViewModel(), KoinComponent {
    fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    private val repository by inject<HomeRepository>()

    private val _adminDetails = MutableStateFlow<Resource<AdminDetails>>(Resource.Loading)
    val adminDetails = _adminDetails.asStateFlow()
    private val _recentPayments = MutableStateFlow<Resource<List<Payment>>>(Resource.Loading)
    val recentPayments = _recentPayments.asStateFlow()

    private val _recentDeals = MutableStateFlow<Resource<List<Deal>>>(Resource.Loading)
    val recentDeals = _recentDeals.asStateFlow()

    init {
        getDetails()
    }

    fun getDetails() {
        viewModelScope.launch {
            repository.getAdminDetails().collectLatest { response ->
                _adminDetails.update {
                    response
                }
            }
            repository.getRecentDeals().collectLatest { response ->
                _recentDeals.update {
                    response
                }
            }
            repository.getRecentPayments().collectLatest { response ->
                _recentPayments.update {
                    response
                }
            }
        }
    }

}