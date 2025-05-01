package com.prafullkumar.propvault.admin.features.deals.domain.repo

import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.core.model.DealWithPayments
import kotlinx.coroutines.flow.Flow

interface DealsRepository {
    fun getAllDeals(): Flow<Resource<List<Deal>>>


    fun getDealWithPayments(dealId: String): Flow<Resource<DealWithPayments>>
}