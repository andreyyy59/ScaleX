package me.proyecto.scalex.data.remote

import me.proyecto.scalex.data.model.Motorcycle
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MotorcycleApiService {

    @GET("v1/motorcycles")
    suspend fun searchMotorcycles(
        @Header("X-Api-Key") apiKey: String,
        @Query("make") make: String? = null,
        @Query("model") model: String? = null,
        @Query("year") year: String? = null
    ): List<Motorcycle>
}