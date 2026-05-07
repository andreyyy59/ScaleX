package me.proyecto.scalex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val motorcycleId: String,
    val userId: String,
    val make: String,
    val model: String,
    val year: String,
    val type: String?,
    val displacement: String?,
    val engine: String?,
    val power: String?,
    val torque: String?,
    val topSpeed: String?,
    val compression: String?,
    val boreStroke: String?,
    val valvesPerCylinder: String?,
    val fuelSystem: String?,
    val fuelControl: String?,
    val ignition: String?,
    val lubrication: String?,
    val cooling: String?,
    val gearbox: String?,
    val transmission: String?,
    val clutch: String?,
    val frame: String?,
    val frontSuspension: String?,
    val frontWheelTravel: String?,
    val rearSuspension: String?,
    val rearWheelTravel: String?,
    val frontTire: String?,
    val rearTire: String?,
    val frontBrakes: String?,
    val rearBrakes: String?,
    val totalWeight: String?,
    val totalHeight: String?,
    val totalLength: String?,
    val totalWidth: String?,
    val seatHeight: String?,
    val wheelbase: String?,
    val groundClearance: String?,
    val fuelCapacity: String?,
    val starter: String?,
    val fuelConsumption: String?,
    val emission: String?,
    val dryWeight: String?,
    val addedAt: Long = System.currentTimeMillis()
)
