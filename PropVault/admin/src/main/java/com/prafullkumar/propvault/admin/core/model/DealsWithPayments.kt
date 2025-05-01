package com.prafullkumar.propvault.admin.core.model

import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment

data class DealWithPayments(
    val deal: Deal, val payments: List<Payment>
)