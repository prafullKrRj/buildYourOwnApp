package com.prafullkumar.propvault.admin.features.deals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.propvault.admin.core.data.sampleData.dealsSample
import com.prafullkumar.propvault.admin.core.data.sampleData.payments
import com.prafullkumar.propvault.admin.core.domain.model.Deal
import com.prafullkumar.propvault.admin.core.domain.model.Payment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DealWithPayments(
    val deal: Deal, val payments: List<Payment>
)

class DealViewModel(
    private val dealId: Int,
) : ViewModel() {


    private val _dealDetails = MutableStateFlow(
        DealWithPayments(
            deal = dealsSample.first { it.id == dealId }, payments = payments.filter { it.dealId == dealId }
        )
    )
    val dealDetails = _dealDetails.asStateFlow()


    init {
        getDealDetails()
    }

    fun getDealDetails() = viewModelScope.launch(Dispatchers.IO) {
        _dealDetails.update {
            it.copy(
                deal = it.deal.copy(
                    id = dealId
                )
            )
        }
    }
}