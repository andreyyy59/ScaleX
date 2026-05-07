package me.proyecto.scalex.data.repository

import me.proyecto.scalex.BuildConfig
import me.proyecto.scalex.core.Result
import me.proyecto.scalex.data.local.MotorcycleDao
import me.proyecto.scalex.data.mapper.toDomain
import me.proyecto.scalex.data.mapper.toEntity
import me.proyecto.scalex.data.remote.MotorcycleApiService
import me.proyecto.scalex.domain.model.Motorcycle
import me.proyecto.scalex.domain.repository.IMotorcycleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MotorcycleRepositoryImpl @Inject constructor(
    private val apiService: MotorcycleApiService,
    private val motorcycleDao: MotorcycleDao
) : IMotorcycleRepository {

    companion object {
        private const val CACHE_DURATION_MS = 60 * 60 * 1000L // 1 hora
    }

    override suspend fun searchMotorcycles(
        make: String?,
        model: String?,
        year: String?
    ): Result<List<Motorcycle>> {
        return try {
            val localResults = mutableListOf<Motorcycle>()

            if (model != null) {
                localResults.addAll(motorcycleDao.searchByModel(model).map { it.toDomain() })
            }
            if (make != null) {
                localResults.addAll(motorcycleDao.searchByMake(make).map { it.toDomain() })
            }
            if (year != null) {
                localResults.addAll(motorcycleDao.searchByYear(year).map { it.toDomain() })
            }

            if (shouldRefreshCache() && localResults.isEmpty()) {
                val remoteResults = fetchFromRemote(make, model, year)
                remoteResults
            } else {
                Result.Success(localResults.distinctBy { it.getId() })
            }
        } catch (e: Exception) {
            try {
                fetchFromRemote(make, model, year)
            } catch (remoteException: Exception) {
                Result.Error(remoteException)
            }
        }
    }

    override suspend fun searchByModel(model: String): Result<List<Motorcycle>> {
        return try {
            val local = motorcycleDao.searchByModel(model)
            if (shouldRefreshCache() || local.isEmpty()) {
                fetchFromRemote(model = model)
            } else {
                Result.Success(local.map { it.toDomain() })
            }
        } catch (e: Exception) {
            fetchFromRemote(model = model)
        }
    }

    override suspend fun searchByMake(make: String): Result<List<Motorcycle>> {
        return try {
            val local = motorcycleDao.searchByMake(make)
            if (shouldRefreshCache() || local.isEmpty()) {
                fetchFromRemote(make = make)
            } else {
                Result.Success(local.map { it.toDomain() })
            }
        } catch (e: Exception) {
            fetchFromRemote(make = make)
        }
    }

    override suspend fun searchByYear(year: String): Result<List<Motorcycle>> {
        return try {
            val local = motorcycleDao.searchByYear(year)
            if (shouldRefreshCache() || local.isEmpty()) {
                fetchFromRemote(year = year)
            } else {
                Result.Success(local.map { it.toDomain() })
            }
        } catch (e: Exception) {
            fetchFromRemote(year = year)
        }
    }

    private suspend fun fetchFromRemote(
        make: String? = null,
        model: String? = null,
        year: String? = null
    ): Result<List<Motorcycle>> {
        return try {
            val response = apiService.searchMotorcycles(
                apiKey = BuildConfig.API_KEY,
                make = make,
                model = model,
                year = year
            )
            val domainModels = response.map { it.toDomain() }
            val entities = domainModels.map { it.toEntity() }
            motorcycleDao.insertAll(entities)
            Result.Success(domainModels)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun shouldRefreshCache(): Boolean {
        val lastUpdated = motorcycleDao.getLastUpdated() ?: return true
        return (System.currentTimeMillis() - lastUpdated) > CACHE_DURATION_MS
    }
}
