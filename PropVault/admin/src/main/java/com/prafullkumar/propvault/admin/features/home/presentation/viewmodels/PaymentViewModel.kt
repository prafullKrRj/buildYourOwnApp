package com.prafullkumar.propvault.admin.features.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.sampleData.dealsSample
import com.prafullkumar.common.sampleData.paymentsSample
import com.prafullkumar.common.sampleData.propertyUnits
import com.prafullkumar.propvault.admin.features.home.domain.model.PaymentWithUnitAndDeal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PaymentViewModel(
) : ViewModel() {

    private val _payments = MutableStateFlow(paymentsSample.map { it.toPaymentWithUnitAndDeal() })
    val payments = _payments.asStateFlow()

}

fun Payment.toPaymentWithUnitAndDeal(): PaymentWithUnitAndDeal {
    val deal = dealsSample.find {
        it.id == this.dealId
    }
    return PaymentWithUnitAndDeal(
        payment = this,
        unit = propertyUnits.find { it.id == deal?.unitId }!!,
        deal = deal!!
    )
}
