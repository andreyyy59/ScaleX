package me.proyecto.scalex.domain.model

data class Motorcycle(
    val make: String = "",
    val model: String = "",
    val year: String = "",
    val type: String? = null,
    val displacement: String? = null,
    val engine: String? = null,
    val power: String? = null,
    val torque: String? = null,
    val topSpeed: String? = null,
    val compression: String? = null,
    val boreStroke: String? = null,
    val valvesPerCylinder: String? = null,
    val fuelSystem: String? = null,
    val fuelControl: String? = null,
    val ignition: String? = null,
    val lubrication: String? = null,
    val cooling: String? = null,
    val gearbox: String? = null,
    val transmission: String? = null,
    val clutch: String? = null,
    val frame: String? = null,
    val frontSuspension: String? = null,
    val frontWheelTravel: String? = null,
    val rearSuspension: String? = null,
    val rearWheelTravel: String? = null,
    val frontTire: String? = null,
    val rearTire: String? = null,
    val frontBrakes: String? = null,
    val rearBrakes: String? = null,
    val totalWeight: String? = null,
    val totalHeight: String? = null,
    val totalLength: String? = null,
    val totalWidth: String? = null,
    val seatHeight: String? = null,
    val wheelbase: String? = null,
    val groundClearance: String? = null,
    val fuelCapacity: String? = null,
    val starter: String? = null,
    val fuelConsumption: String? = null,
    val emission: String? = null,
    val dryWeight: String? = null
) {
    fun getFullName(): String = "$make $model $year".trim()

    fun getId(): String = "${make}_${model}_${year}".replace(" ", "_").lowercase()

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

    fun getPowerInHP(): Float? {
        return power?.substringBefore("HP")?.trim()?.toFloatOrNull()
    }

    fun getDisplacementInCC(): Float? {
        return displacement?.substringBefore("ccm")?.trim()?.toFloatOrNull()
    }

    fun getTorqueInNm(): Float? {
        return torque?.substringBefore("Nm")?.trim()?.toFloatOrNull()
    }
}

private fun String.extractNumber(): Float? {
    return this.replace("[^0-9.]".toRegex(), "").toFloatOrNull()
}
