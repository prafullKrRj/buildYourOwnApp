package com.prafullkumar.propvault.admin.features.home.domain.repository

import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.home.domain.model.AdminDetails
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun getAdminDetails(): Flow<Resource<AdminDetails>>

    fun getRecentDeals(): Flow<Resource<List<Deal>>>

    fun getRecentPayments(): Flow<Resource<List<Payment>>>
}
