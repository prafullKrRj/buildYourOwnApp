package com.prafullkumar.propvault.admin.features.developments.data

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.prafullkumar.common.models.Building
import com.prafullkumar.common.models.Development
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class DevelopmentRepositoryImpl : DevelopmentsRepository {
    private val db = FirebaseFirestore.getInstance()

    override fun getAllDevelopments(): Flow<Resource<List<Development>>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = db.collection("developments").get().await()
                val developments = response.documents.mapNotNull { document ->
                    document.let {
                        Development(
                            id = it.id,
                            name = it.getString("name") ?: return@mapNotNull null,
                            category = it.getString("category") ?: return@mapNotNull null,
                            website = it.getString("website") ?: return@mapNotNull null,
                            city = it.getString("city") ?: return@mapNotNull null,
                            attorneyFirmName = it.getString("attorneyFirmName")
                                ?: return@mapNotNull null,
                            brokerageFeeCommission = it.getDouble("brokerageFeeCommission")?.toInt()
                                ?: return@mapNotNull null,
                            status = it.getString("status") ?: return@mapNotNull null,
                            startingPrice = it.getDouble("startingPrice") ?: return@mapNotNull null,
                            numberOfBuildings = it.getLong("numberOfBuildings")?.toInt()
                                ?: return@mapNotNull null,
                            imageUrl = it.getString("imageUrl"),
                            buildings = it.get("buildings") as? List<String> ?: emptyList()
                        )
                    }
                }
                emit(Resource.Success(developments))
            } catch (e: Exception) {
                Log.d("Developments", "Error fetching developments: ${e.message}")
                emit(Resource.Error("Error fetching developments: ${e.message}"))
            }
        }
    }

    override fun getBuildings(developmentId: String): Flow<Resource<List<Building>>> {
        return flow {
            emit(Resource.Loading)
            try {
                Log.d("GetBuildings", "Development ID: $developmentId")
                val response = db.collection("developments").document(developmentId).get().await()
                val buildings = response.get("buildings") as? List<String> ?: emptyList()
                Log.d("GetBuildings", "Building IDs: $buildings")
                val buildingsList = buildings.mapNotNull { buildingId ->
                    db.collection("buildings").document(buildingId).get().await().let {
                        Building(
                            id = it.id,
                            name = it.getString("name") ?: return@let null,
                            developmentId = it.getString("developmentId") ?: return@let null,
                            developmentName = it.getString("developmentName") ?: return@let null,
                            units = it.get("units") as? List<String> ?: emptyList(),
                            lastUpdateTime = it.get("lastUpdateTime") as? String ?: "",
                            unitsCount = it.getLong("unitsCount")?.toInt() ?: return@let null,
                        )
                    }
                }
                Log.d("GetBuildings", "Buildings: $buildingsList")
                emit(Resource.Success(buildingsList))
            } catch (e: Exception) {
                Log.d("Developments", "Error fetching buildings: ${e.message}")
                emit(Resource.Error("Error fetching buildings: ${e.message}"))
            }
        }
    }

    override fun getUnits(buildingId: String): Flow<Resource<List<PropertyUnit>>> {
        return flow {
            emit(Resource.Loading)
            try {
                Log.d("GetUnits", "Building ID: $buildingId")
                val response = db.collection("buildings").document(buildingId).get().await()
                val units = response.get("units") as? List<String> ?: emptyList()
                Log.d("GetUnits", "Unit IDs: $units")
                val unitsList = units.mapNotNull { unitId ->
                    db.collection("units").document(unitId).get().await().let {
                        PropertyUnit(
                            id = it.id,
                            name = it.getString("name") ?: return@let null,
                            buildingId = it.getString("buildingId") ?: return@let null,
                            buildingName = it.getString("buildingName") ?: return@let null,
                            developmentId = it.getString("developmentId") ?: return@let null,
                            developmentName = it.getString("developmentName") ?: return@let null,
                            unitType = it.getString("unitType") ?: return@let null,
                            price = it.getDouble("price") ?: return@let null,
                            status = it.getString("status") ?: return@let null,
                            createdTime = it.get("createdTime") as? String ?: "",
                            deals = it.get("deals") as? List<String> ?: emptyList(),
                            unitCode = it.getString("unitCode") ?: return@let null,
                        )
                    }
                }
                Log.d("GetUnits", "Units: $unitsList")
                emit(Resource.Success(unitsList))
            } catch (e: Exception) {
                Log.d("Developments", "Error fetching units: ${e.message}")
                emit(Resource.Error("Error fetching units: ${e.message}"))
            }
        }
    }

    override fun addDevelopment(development: Development): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading)
                db.runTransaction { transaction ->
                    val developmentId = db.collection("developments").document().id
                    val developmentRef = db.collection("developments").document(developmentId)
                    val adminRef = db.collection("admin").document("adminDetails")

                    transaction.set(developmentRef, development.copy(id = developmentId))
                    transaction.update(adminRef, "totalDevelopments", FieldValue.increment(1))
                }.await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                Log.d("Developments", "Error adding development: ${e.message}")
                emit(Resource.Error("Error adding development: ${e.message}"))
            }
        }
    }

    override fun addBuilding(building: Building): Flow<Resource<Boolean>> {
        return flow {
            try {
                Log.d("AddBuilding", "Adding building: $building")
                db.runTransaction {
                    val buildingId = db.collection("buildings").document().id
                    val developmentRef =
                        db.collection("developments").document(building.developmentId)
                    val buildingData = building.copy(id = buildingId)
                    it.set(db.collection("buildings").document(buildingId), buildingData)
                    it.update(developmentRef, "buildings", FieldValue.arrayUnion(buildingId))
                }
                emit(Resource.Success(true))
            } catch (e: Exception) {
                Log.d("AddBuilding", "Error adding building: ${e.message}")
                emit(Resource.Error("Error adding building: ${e.message}"))
            }
        }
    }

    override fun addUnit(unit: PropertyUnit): Flow<Resource<Boolean>> {
        return flow {

        }
    }
}