package me.proyecto.scalex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MotorcycleEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MotorcycleDatabase : RoomDatabase() {
    abstract fun motorcycleDao(): MotorcycleDao
    abstract fun favoriteDao(): FavoriteDao
}
