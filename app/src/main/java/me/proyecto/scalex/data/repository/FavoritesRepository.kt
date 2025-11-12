package me.proyecto.scalex.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import me.proyecto.scalex.data.model.Motorcycle

class FavoritesRepository(context: Context) {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    companion object {
        @Volatile
        private var instance: FavoritesRepository? = null

        fun getInstance(context: Context): FavoritesRepository {
            return instance ?: synchronized(this) {
                instance ?: FavoritesRepository(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    // Obtener referencia a la colección de favoritos del usuario actual
    private fun getFavoritesCollection() =
        firestore.collection("users")
            .document(getCurrentUserId())
            .collection("favorites")

    // Obtener ID del usuario actual
    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: "anonymous"
        // Si no hay usuario autenticado, usa "anonymous"
    }

    /**
     * Obtener todos los favoritos del usuario
     */
    suspend fun getAllFavorites(): List<Motorcycle> {
        return try {
            val snapshot = getFavoritesCollection()
                .orderBy("addedAt", Query.Direction.DESCENDING)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Motorcycle::class.java)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Agregar una moto a favoritos
     */
    suspend fun addFavorite(motorcycle: Motorcycle): Boolean {
        return try {
            val motorcycleMap = motorcycle.toMap().toMutableMap()
            motorcycleMap["addedAt"] = com.google.firebase.Timestamp.now()

            getFavoritesCollection()
                .document(motorcycle.getId())
                .set(motorcycleMap)
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Eliminar una moto de favoritos
     */
    suspend fun removeFavorite(motorcycleId: String): Boolean {
        return try {
            getFavoritesCollection()
                .document(motorcycleId)
                .delete()
                .await()

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Verificar si una moto está en favoritos
     */
    suspend fun isFavorite(motorcycleId: String): Boolean {
        return try {
            val doc = getFavoritesCollection()
                .document(motorcycleId)
                .get()
                .await()

            doc.exists()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Obtener una moto favorita por ID
     */
    suspend fun getFavoriteById(motorcycleId: String): Motorcycle? {
        return try {
            val doc = getFavoritesCollection()
                .document(motorcycleId)
                .get()
                .await()

            doc.toObject(Motorcycle::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Limpiar todos los favoritos
     */
    suspend fun clearAllFavorites(): Boolean {
        return try {
            val snapshot = getFavoritesCollection().get().await()

            snapshot.documents.forEach { doc ->
                doc.reference.delete().await()
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Obtener cantidad de favoritos
     */
    suspend fun getFavoritesCount(): Int {
        return try {
            val snapshot = getFavoritesCollection().get().await()
            snapshot.size()
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Toggle favorito (agregar si no existe, eliminar si existe)
     */
    suspend fun toggleFavorite(motorcycle: Motorcycle): Boolean {
        return if (isFavorite(motorcycle.getId())) {
            removeFavorite(motorcycle.getId())
        } else {
            addFavorite(motorcycle)
        }
    }

    /**
     * Obtener IDs de favoritos
     */
    suspend fun getFavoriteIds(): Set<String> {
        return try {
            val snapshot = getFavoritesCollection().get().await()
            snapshot.documents.map { it.id }.toSet()
        } catch (e: Exception) {
            emptySet()
        }
    }
    fun observeFavorites(onUpdate: (List<Motorcycle>) -> Unit) {
        getFavoritesCollection()
            .orderBy("addedAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onUpdate(emptyList())
                    return@addSnapshotListener
                }

                val favorites = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Motorcycle::class.java)
                } ?: emptyList()

                onUpdate(favorites)
            }
    }
}

// Extensión para convertir Motorcycle a Map
private fun Motorcycle.toMap(): Map<String, Any?> {
    return hashMapOf(
        "make" to make,
        "model" to model,
        "year" to year,
        "type" to type,
        "displacement" to displacement,
        "engine" to engine,
        "power" to power,
        "torque" to torque,
        "topSpeed" to topSpeed,
        "compression" to compression,
        "boreStroke" to boreStroke,
        "valvesPerCylinder" to valvesPerCylinder,
        "fuelSystem" to fuelSystem,
        "fuelControl" to fuelControl,
        "ignition" to ignition,
        "lubrication" to lubrication,
        "cooling" to cooling,
        "gearbox" to gearbox,
        "transmission" to transmission,
        "clutch" to clutch,
        "frame" to frame,
        "frontSuspension" to frontSuspension,
        "frontWheelTravel" to frontWheelTravel,
        "rearSuspension" to rearSuspension,
        "rearWheelTravel" to rearWheelTravel,
        "frontTire" to frontTire,
        "rearTire" to rearTire,
        "frontBrakes" to frontBrakes,
        "rearBrakes" to rearBrakes,
        "totalWeight" to totalWeight,
        "totalHeight" to totalHeight,
        "totalLength" to totalLength,
        "totalWidth" to totalWidth,
        "seatHeight" to seatHeight,
        "wheelbase" to wheelbase,
        "groundClearance" to groundClearance,
        "fuelCapacity" to fuelCapacity,
        "starter" to starter,
        "fuelConsumption" to fuelConsumption,
        "emission" to emission,
        "dryWeight" to dryWeight
    )
}
