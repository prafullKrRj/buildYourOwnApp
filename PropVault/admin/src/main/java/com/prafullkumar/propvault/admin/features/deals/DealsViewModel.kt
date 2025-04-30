package com.prafullkumar.propvault.admin.features.deals


import androidx.lifecycle.ViewModel
import com.prafullkumar.propvault.admin.core.data.sampleData.dealsSample
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class DealsViewModel(
) : ViewModel() {

    private val _deals = MutableStateFlow(dealsSample)
    val deals = _deals.asStateFlow()


}