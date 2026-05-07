package me.proyecto.scalex.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import me.proyecto.scalex.data.local.FavoriteDao
import me.proyecto.scalex.data.mapper.toDomain
import me.proyecto.scalex.data.mapper.toFavoriteEntity
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.repository.IUserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : IUserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    override val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid

    override suspend fun register(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override fun logout() {
        firebaseAuth.signOut()
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    private fun getUserId(): String {
        return firebaseAuth.currentUser?.uid ?: "anonymous"
    }

    override suspend fun getAllFavorites(): List<Motorcycle> {
        return try {
            val local = favoriteDao.getAllFavorites(getUserId())
            if (local.isNotEmpty()) {
                return local.map { it.toDomain() }
            }
            val remote = fetchFavoritesFromFirestore()
            remote
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addFavorite(motorcycle: Motorcycle): Boolean {
        return try {
            val userId = getUserId()
            favoriteDao.addFavorite(motorcycle.toFavoriteEntity(userId))
            syncToFirestore(motorcycle)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun removeFavorite(motorcycleId: String): Boolean {
        return try {
            val userId = getUserId()
            favoriteDao.removeFavorite(motorcycleId, userId)
            firestore.collection("users")
                .document(userId)
                .collection("favorites")
                .document(motorcycleId)
                .delete()
                .await()
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun isFavorite(motorcycleId: String): Boolean {
        return try {
            favoriteDao.isFavorite(motorcycleId, getUserId())
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getFavoriteIds(): Set<String> {
        return try {
            favoriteDao.getFavoriteIds(getUserId()).toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }

    override suspend fun toggleFavorite(motorcycle: Motorcycle): Boolean {
        return if (isFavorite(motorcycle.getId())) {
            removeFavorite(motorcycle.getId())
        } else {
            addFavorite(motorcycle)
        }
    }

    private suspend fun fetchFavoritesFromFirestore(): List<Motorcycle> {
        return try {
            val snapshot = firestore.collection("users")
                .document(getUserId())
                .collection("favorites")
                .orderBy("addedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Motorcycle::class.java)
            }.also { list ->
                list.forEach { bike ->
                    favoriteDao.addFavorite(bike.toFavoriteEntity(getUserId()))
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun syncToFirestore(motorcycle: Motorcycle) {
        try {
            val map = motorcycleToMap(motorcycle)
            firestore.collection("users")
                .document(getUserId())
                .collection("favorites")
                .document(motorcycle.getId())
                .set(map)
                .await()
        } catch (_: Exception) {
        }
    }

    private fun motorcycleToMap(motorcycle: Motorcycle): Map<String, Any?> {
        return hashMapOf(
            "make" to motorcycle.make,
            "model" to motorcycle.model,
            "year" to motorcycle.year,
            "type" to motorcycle.type,
            "displacement" to motorcycle.displacement,
            "engine" to motorcycle.engine,
            "power" to motorcycle.power,
            "torque" to motorcycle.torque,
            "topSpeed" to motorcycle.topSpeed,
            "compression" to motorcycle.compression,
            "boreStroke" to motorcycle.boreStroke,
            "valvesPerCylinder" to motorcycle.valvesPerCylinder,
            "fuelSystem" to motorcycle.fuelSystem,
            "fuelControl" to motorcycle.fuelControl,
            "ignition" to motorcycle.ignition,
            "lubrication" to motorcycle.lubrication,
            "cooling" to motorcycle.cooling,
            "gearbox" to motorcycle.gearbox,
            "transmission" to motorcycle.transmission,
            "clutch" to motorcycle.clutch,
            "frame" to motorcycle.frame,
            "frontSuspension" to motorcycle.frontSuspension,
            "frontWheelTravel" to motorcycle.frontWheelTravel,
            "rearSuspension" to motorcycle.rearSuspension,
            "rearWheelTravel" to motorcycle.rearWheelTravel,
            "frontTire" to motorcycle.frontTire,
            "rearTire" to motorcycle.rearTire,
            "frontBrakes" to motorcycle.frontBrakes,
            "rearBrakes" to motorcycle.rearBrakes,
            "totalWeight" to motorcycle.totalWeight,
            "totalHeight" to motorcycle.totalHeight,
            "totalLength" to motorcycle.totalLength,
            "totalWidth" to motorcycle.totalWidth,
            "seatHeight" to motorcycle.seatHeight,
            "wheelbase" to motorcycle.wheelbase,
            "groundClearance" to motorcycle.groundClearance,
            "fuelCapacity" to motorcycle.fuelCapacity,
            "starter" to motorcycle.starter,
            "fuelConsumption" to motorcycle.fuelConsumption,
            "emission" to motorcycle.emission,
            "dryWeight" to motorcycle.dryWeight,
            "addedAt" to com.google.firebase.Timestamp.now()
        )
    }
}
