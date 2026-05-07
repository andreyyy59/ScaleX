package me.proyecto.scalex.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "motorcycles")
data class MotorcycleEntity(
    @PrimaryKey
    val id: String,
    val make: String,
    val model: String,
    val year: String,
    val type: String?,
    val displacement: String?,
    val engine: String?,
    val power: String?,
    val torque: String?,
    @ColumnInfo(name = "top_speed")
    val topSpeed: String?,
    val compression: String?,
    @ColumnInfo(name = "bore_stroke")
    val boreStroke: String?,
    @ColumnInfo(name = "valves_per_cylinder")
    val valvesPerCylinder: String?,
    @ColumnInfo(name = "fuel_system")
    val fuelSystem: String?,
    @ColumnInfo(name = "fuel_control")
    val fuelControl: String?,
    val ignition: String?,
    val lubrication: String?,
    val cooling: String?,
    val gearbox: String?,
    val transmission: String?,
    val clutch: String?,
    val frame: String?,
    @ColumnInfo(name = "front_suspension")
    val frontSuspension: String?,
    @ColumnInfo(name = "front_wheel_travel")
    val frontWheelTravel: String?,
    @ColumnInfo(name = "rear_suspension")
    val rearSuspension: String?,
    @ColumnInfo(name = "rear_wheel_travel")
    val rearWheelTravel: String?,
    @ColumnInfo(name = "front_tire")
    val frontTire: String?,
    @ColumnInfo(name = "rear_tire")
    val rearTire: String?,
    @ColumnInfo(name = "front_brakes")
    val frontBrakes: String?,
    @ColumnInfo(name = "rear_brakes")
    val rearBrakes: String?,
    @ColumnInfo(name = "total_weight")
    val totalWeight: String?,
    @ColumnInfo(name = "total_height")
    val totalHeight: String?,
    @ColumnInfo(name = "total_length")
    val totalLength: String?,
    @ColumnInfo(name = "total_width")
    val totalWidth: String?,
    @ColumnInfo(name = "seat_height")
    val seatHeight: String?,
    val wheelbase: String?,
    @ColumnInfo(name = "ground_clearance")
    val groundClearance: String?,
    @ColumnInfo(name = "fuel_capacity")
    val fuelCapacity: String?,
    val starter: String?,
    @ColumnInfo(name = "fuel_consumption")
    val fuelConsumption: String?,
    val emission: String?,
    @ColumnInfo(name = "dry_weight")
    val dryWeight: String?,
    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long = System.currentTimeMillis()
)
