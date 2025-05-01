package com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.common.models.Development
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DevelopmentViewModel(

) : ViewModel(), KoinComponent {

    private val repository by inject<DevelopmentsRepository>()
    val state = MutableStateFlow(false)

    private val _developments = MutableStateFlow<Resource<List<Development>>>(Resource.Loading)
    val developments = _developments.asStateFlow()


    init {
        getDevelopments()
    }

    fun getDevelopments() {
        viewModelScope.launch {
            repository.getAllDevelopments().collectLatest { response ->
                _developments.update {
                    response
                }
            }
        }
    }
}