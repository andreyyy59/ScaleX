package me.proyecto.scalex.domain.usecase

import me.proyecto.scalex.core.Result
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.repository.IMotorcycleRepository
import javax.inject.Inject

class GetMotorcyclesUseCase @Inject constructor(
    private val repository: IMotorcycleRepository
) {
    suspend fun byModel(model: String): Result<List<Motorcycle>> {
        return repository.searchByModel(model)
    }

    suspend fun byMake(make: String): Result<List<Motorcycle>> {
        return repository.searchByMake(make)
    }

    suspend fun byYear(year: String): Result<List<Motorcycle>> {
        return repository.searchByYear(year)
    }
}
