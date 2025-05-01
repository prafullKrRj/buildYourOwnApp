package com.prafullkumar.propvault.admin.features.deals.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.core.model.DealWithPayments
import com.prafullkumar.propvault.admin.features.deals.domain.repo.DealsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DealViewModel(
    private val dealId: String,
) : ViewModel(), KoinComponent {

    private val repository by inject<DealsRepository>()

    private val _dealDetails = MutableStateFlow<Resource<DealWithPayments>>(Resource.Loading)
    val dealDetails = _dealDetails.asStateFlow()


    init {
        getDealDetails()
    }

    fun getDealDetails() = viewModelScope.launch(Dispatchers.IO) {
        viewModelScope.launch {
            repository.getDealWithPayments(dealId).collectLatest { response ->
                _dealDetails.update {
                    response
                }
            }
        }
    }
}