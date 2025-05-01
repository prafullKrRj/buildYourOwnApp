package com.prafullkumar.propvault.admin.features.developments.domain

import com.prafullkumar.common.models.Building
import com.prafullkumar.common.models.Development
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DevelopmentsRepository {
    fun getAllDevelopments(): Flow<Resource<List<Development>>>

    fun getBuildings(developmentId: String): Flow<Resource<List<Building>>>

    fun getUnits(buildingId: String): Flow<Resource<List<PropertyUnit>>>


    fun addDevelopment(development: Development): Flow<Resource<Boolean>>

    fun addBuilding(building: Building): Flow<Resource<Boolean>>

    fun addUnit(unit: PropertyUnit): Flow<Resource<Boolean>>
}