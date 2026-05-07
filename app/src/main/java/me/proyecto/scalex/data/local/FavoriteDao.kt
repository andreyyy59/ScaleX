package me.proyecto.scalex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY addedAt DESC")
    suspend fun getAllFavorites(userId: String): List<FavoriteEntity>

    @Query("SELECT * FROM favorites WHERE motorcycleId = :motorcycleId AND userId = :userId")
    suspend fun getFavorite(motorcycleId: String, userId: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE motorcycleId = :motorcycleId AND userId = :userId")
    suspend fun removeFavorite(motorcycleId: String, userId: String)

    @Query("SELECT motorcycleId FROM favorites WHERE userId = :userId")
    suspend fun getFavoriteIds(userId: String): List<String>

    @Query("DELETE FROM favorites WHERE userId = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT COUNT(*) FROM favorites WHERE userId = :userId")
    suspend fun getCount(userId: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE motorcycleId = :motorcycleId AND userId = :userId)")
    suspend fun isFavorite(motorcycleId: String, userId: String): Boolean
}
