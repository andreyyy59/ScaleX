package me.proyecto.scalex.domain.repository

import me.proyecto.scalex.core.Result
import me.proyecto.scalex.domain.model.Motorcycle

interface IMotorcycleRepository {
    suspend fun searchMotorcycles(
        make: String? = null,
        model: String? = null,
        year: String? = null
    ): Result<List<Motorcycle>>

    suspend fun searchByModel(model: String): Result<List<Motorcycle>>
    suspend fun searchByMake(make: String): Result<List<Motorcycle>>
    suspend fun searchByYear(year: String): Result<List<Motorcycle>>
}
