// data/model/Motorcycle.kt

package me.proyecto.scalex.data.model

import com.google.gson.annotations.SerializedName

data class Motorcycle(
    @SerializedName("make")
    val make: String,

    @SerializedName("model")
    val model: String,

    @SerializedName("year")
    val year: String,

    @SerializedName("type")
    val type: String?,

    @SerializedName("displacement")
    val displacement: String?,

    @SerializedName("engine")
    val engine: String?,

    @SerializedName("power")
    val power: String?,

    @SerializedName("torque")
    val torque: String?,

    @SerializedName("top_speed")
    val topSpeed: String?,

    @SerializedName("compression")
    val compression: String?,

    @SerializedName("bore_stroke")
    val boreStroke: String?,

    @SerializedName("valves_per_cylinder")
    val valvesPerCylinder: String?,

    @SerializedName("fuel_system")
    val fuelSystem: String?,

    @SerializedName("fuel_control")
    val fuelControl: String?,

    @SerializedName("ignition")
    val ignition: String?,

    @SerializedName("lubrication")
    val lubrication: String?,

    @SerializedName("cooling")
    val cooling: String?,

    @SerializedName("gearbox")
    val gearbox: String?,

    @SerializedName("transmission")
    val transmission: String?,

    @SerializedName("clutch")
    val clutch: String?,

    @SerializedName("frame")
    val frame: String?,

    @SerializedName("front_suspension")
    val frontSuspension: String?,

    @SerializedName("front_wheel_travel")
    val frontWheelTravel: String?,

    @SerializedName("rear_suspension")
    val rearSuspension: String?,

    @SerializedName("rear_wheel_travel")
    val rearWheelTravel: String?,

    @SerializedName("front_tire")
    val frontTire: String?,

    @SerializedName("rear_tire")
    val rearTire: String?,

    @SerializedName("front_brakes")
    val frontBrakes: String?,

    @SerializedName("rear_brakes")
    val rearBrakes: String?,

    @SerializedName("total_weight")
    val totalWeight: String?,

    @SerializedName("total_height")
    val totalHeight: String?,

    @SerializedName("total_length")
    val totalLength: String?,

    @SerializedName("total_width")
    val totalWidth: String?,

    @SerializedName("seat_height")
    val seatHeight: String?,

    @SerializedName("wheelbase")
    val wheelbase: String?,

    @SerializedName("ground_clearance")
    val groundClearance: String?,

    @SerializedName("fuel_capacity")
    val fuelCapacity: String?,

    @SerializedName("starter")
    val starter: String?,

    @SerializedName("fuel_consumption")
    val fuelConsumption: String?,

    @SerializedName("emission")
    val emission: String?,

    @SerializedName("dry_weight")
    val dryWeight: String?
) {
    // Nombre completo para mostrar
    fun getFullName(): String = "$make $model $year".trim()

    // ID único para favoritos
    fun getId(): String = "${make}_${model}_${year}".replace(" ", "_").lowercase()

    // Extraer valores numéricos para comparación de tamaños
    fun getLengthInMm(): Float? {
        return totalLength?.extractNumber()
    }

    fun getWidthInMm(): Float? {
        return totalWidth?.extractNumber()
    }

    fun getHeightInMm(): Float? {
        return totalHeight?.extractNumber()
    }

    fun getWeightInKg(): Float? {
        return totalWeight?.extractNumber()
    }

    // Extraer potencia en HP
    fun getPowerInHP(): Float? {
        return power?.substringBefore("HP")?.trim()?.toFloatOrNull()
    }

    // Extraer cilindrada en cc
    fun getDisplacementInCC(): Float? {
        return displacement?.substringBefore("ccm")?.trim()?.toFloatOrNull()
    }

    // Extraer torque en Nm
    fun getTorqueInNm(): Float? {
        return torque?.substringBefore("Nm")?.trim()?.toFloatOrNull()
    }
}

// Función de extensión para extraer números de strings
private fun String.extractNumber(): Float? {
    return this.replace("[^0-9.]".toRegex(), "").toFloatOrNull()
}