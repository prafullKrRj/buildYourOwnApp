package com.prafullkumar.propvault.customer.app.data

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.sampleData.PropertyStatus
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.customer.app.domain.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class CustomerRepositoryImpl : CustomerRepository {

    private val firestore = FirebaseFirestore.getInstance()

    override fun getProperties(): Flow<Resource<List<PropertyUnit>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val propertyUnits = firestore.collection("units")
                    .whereNotEqualTo("status", PropertyStatus.SOLD.status).get().await()
                val propertyUnitList = propertyUnits.documents.map { it.toPropertyUnit() }
                emit(Resource.Success(propertyUnitList))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

    override fun getProperty(propertyId: String): Flow<Resource<PropertyUnit>> {
        return flow {
            emit(Resource.Loading)
            try {
                val propertyUnit = firestore.collection("units").document(propertyId).get().await()
                if (propertyUnit.exists()) {
                    emit(Resource.Success(propertyUnit.toPropertyUnit()))
                } else {
                    emit(Resource.Error("Property not found"))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unknown error occurred"))
            }
        }
    }

}

fun DocumentSnapshot?.toPropertyUnit(): PropertyUnit {
    return this?.let {
        PropertyUnit(
            id = it.id,
            name = it.get("name") as String,
            unitCode = it.get("unitCode") as String,
            developmentId = it.get("developmentId") as String,
            developmentName = it.get("developmentName") as String,
            buildingId = it.get("buildingId") as String,
            buildingName = it.get("buildingName") as String,
            unitType = it.get("unitType") as String,
            price = it.get("price") as Double,
            status = it.get("status") as String,
            createdTime = it.get("createdTime") as String,
            deals = (it.get("deals") as? List<String>) ?: emptyList()
        )
    } ?: throw IllegalArgumentException("DocumentReference is null")
}