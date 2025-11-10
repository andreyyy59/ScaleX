package me.proyecto.scalex.data.repository

import me.proyecto.scalex.data.model.Motorcycle
import me.proyecto.scalex.data.remote.MotorcycleApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MotorcycleRepository {

    private val apiService: MotorcycleApiService

    private val API_KEY = "Nn1tJg2DVlUTpuzcqzNydg==Vtl2DTL9P4zrTZox"

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(MotorcycleApiService::class.java)
    }

    suspend fun searchMotorcycles(
        make: String? = null,
        model: String? = null,
        year: String? = null
    ): Result<List<Motorcycle>> {
        return try {
            val response = apiService.searchMotorcycles(
                apiKey = API_KEY,
                make = make,
                model = model,
                year = year
            )
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchByModel(model: String): Result<List<Motorcycle>> {
        return searchMotorcycles(model = model)
    }

    suspend fun searchByMake(make: String): Result<List<Motorcycle>> {
        return searchMotorcycles(make = make)
    }

    suspend fun searchByYear(year: String): Result<List<Motorcycle>> {
        return searchMotorcycles(year = year)
    }
}