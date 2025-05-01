package com.prafullkumar.propvault.admin.features.home.domain.model

import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PropertyUnit


data class PaymentWithUnitAndDeal(
    val payment: Payment,
    val deal: Deal,
    val unit: PropertyUnit
)