package com.prafullkumar.propvault.customer.app.domain

import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    /**
     * Retrieves a list of all available property units that are not sold.
     * @return Flow of Resource containing list of PropertyUnit objects
     */
    fun getProperties(): Flow<Resource<List<PropertyUnit>>>

    /**
     * Retrieves a specific property unit by its ID.
     * @param propertyId The unique identifier of the property
     * @return Flow of Resource containing the requested PropertyUnit object
     */
    fun getProperty(propertyId: String): Flow<Resource<PropertyUnit>>
}
