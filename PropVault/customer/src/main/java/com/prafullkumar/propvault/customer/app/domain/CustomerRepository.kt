package com.prafullkumar.propvault.customer.app.domain

import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getProperties(): Flow<Resource<List<PropertyUnit>>>
    fun getProperty(propertyId: String): Flow<Resource<PropertyUnit>>
}
