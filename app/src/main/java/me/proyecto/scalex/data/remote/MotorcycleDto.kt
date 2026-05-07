package me.proyecto.scalex.data.remote

import com.google.gson.annotations.SerializedName

data class MotorcycleDto(
    @SerializedName("make")
    val make: String = "",
    @SerializedName("model")
    val model: String = "",
    @SerializedName("year")
    val year: String = "",
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("displacement")
    val displacement: String? = null,
    @SerializedName("engine")
    val engine: String? = null,
    @SerializedName("power")
    val power: String? = null,
    @SerializedName("torque")
    val torque: String? = null,
    @SerializedName("top_speed")
    val topSpeed: String? = null,
    @SerializedName("compression")
    val compression: String? = null,
    @SerializedName("bore_stroke")
    val boreStroke: String? = null,
    @SerializedName("valves_per_cylinder")
    val valvesPerCylinder: String? = null,
    @SerializedName("fuel_system")
    val fuelSystem: String? = null,
    @SerializedName("fuel_control")
    val fuelControl: String? = null,
    @SerializedName("ignition")
    val ignition: String? = null,
    @SerializedName("lubrication")
    val lubrication: String? = null,
    @SerializedName("cooling")
    val cooling: String? = null,
    @SerializedName("gearbox")
    val gearbox: String? = null,
    @SerializedName("transmission")
    val transmission: String? = null,
    @SerializedName("clutch")
    val clutch: String? = null,
    @SerializedName("frame")
    val frame: String? = null,
    @SerializedName("front_suspension")
    val frontSuspension: String? = null,
    @SerializedName("front_wheel_travel")
    val frontWheelTravel: String? = null,
    @SerializedName("rear_suspension")
    val rearSuspension: String? = null,
    @SerializedName("rear_wheel_travel")
    val rearWheelTravel: String? = null,
    @SerializedName("front_tire")
    val frontTire: String? = null,
    @SerializedName("rear_tire")
    val rearTire: String? = null,
    @SerializedName("front_brakes")
    val frontBrakes: String? = null,
    @SerializedName("rear_brakes")
    val rearBrakes: String? = null,
    @SerializedName("total_weight")
    val totalWeight: String? = null,
    @SerializedName("total_height")
    val totalHeight: String? = null,
    @SerializedName("total_length")
    val totalLength: String? = null,
    @SerializedName("total_width")
    val totalWidth: String? = null,
    @SerializedName("seat_height")
    val seatHeight: String? = null,
    @SerializedName("wheelbase")
    val wheelbase: String? = null,
    @SerializedName("ground_clearance")
    val groundClearance: String? = null,
    @SerializedName("fuel_capacity")
    val fuelCapacity: String? = null,
    @SerializedName("starter")
    val starter: String? = null,
    @SerializedName("fuel_consumption")
    val fuelConsumption: String? = null,
    @SerializedName("emission")
    val emission: String? = null,
    @SerializedName("dry_weight")
    val dryWeight: String? = null
)

fun MotorcycleDto.getId(): String = "${make}_${model}_${year}".replace(" ", "_").lowercase()
