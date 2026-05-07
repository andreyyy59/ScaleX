package me.proyecto.scalex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MotorcycleDao {
    @Query("SELECT * FROM motorcycles WHERE model LIKE '%' || :model || '%'")
    suspend fun searchByModel(model: String): List<MotorcycleEntity>

    @Query("SELECT * FROM motorcycles WHERE make = :make")
    suspend fun searchByMake(make: String): List<MotorcycleEntity>

    @Query("SELECT * FROM motorcycles WHERE year = :year")
    suspend fun searchByYear(year: String): List<MotorcycleEntity>

    @Query("SELECT * FROM motorcycles WHERE id = :id")
    suspend fun getById(id: String): MotorcycleEntity?

    @Query("SELECT * FROM motorcycles")
    suspend fun getAll(): List<MotorcycleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(motorcycles: List<MotorcycleEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(motorcycle: MotorcycleEntity)

    @Query("DELETE FROM motorcycles")
    suspend fun deleteAll()

    @Query("SELECT MAX(last_updated) FROM motorcycles")
    suspend fun getLastUpdated(): Long?
}
