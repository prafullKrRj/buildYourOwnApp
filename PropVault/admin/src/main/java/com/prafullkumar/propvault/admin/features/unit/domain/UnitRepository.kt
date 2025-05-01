package com.prafullkumar.propvault.admin.features.unit.domain

import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UnitRepository {
    fun addUnit(propertyUnit: PropertyUnit, deal: Deal?, payment: Payment?): Flow<Resource<Boolean>>

    fun getUnitDetails(unitId: String): Flow<Resource<UnitWithDealsAndPayments>>
    fun updateUnit(
        propertyUnit: PropertyUnit,
        deal: Deal?,
        payment: Payment?
    ): Flow<Resource<Boolean>>

}
