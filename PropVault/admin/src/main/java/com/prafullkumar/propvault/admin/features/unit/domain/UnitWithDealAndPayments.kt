package com.prafullkumar.propvault.admin.features.unit.domain

import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.propvault.admin.core.model.DealWithPayments

data class UnitWithDealsAndPayments(
    val unit: PropertyUnit,
    val deals: List<DealWithPayments>
)