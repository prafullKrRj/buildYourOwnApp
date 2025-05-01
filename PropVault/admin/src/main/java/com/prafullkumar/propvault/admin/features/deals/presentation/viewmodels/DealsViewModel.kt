package com.prafullkumar.propvault.admin.features.deals.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.deals.domain.repo.DealsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DealsViewModel(
) : ViewModel(), KoinComponent {

    private val repository by inject<DealsRepository>()
    private val _deals = MutableStateFlow<Resource<List<Deal>>>(Resource.Loading)
    val deals = _deals.asStateFlow()


    init {
        getDeals()
    }

    fun getDeals() {
        viewModelScope.launch {
            repository.getAllDeals().collectLatest { response ->
                _deals.update {
                    response
                }
            }
        }
    }
}