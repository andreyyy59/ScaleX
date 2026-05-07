package me.proyecto.scalex.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.proyecto.scalex.data.local.FavoriteDao
import me.proyecto.scalex.data.local.MotorcycleDao
import me.proyecto.scalex.data.local.MotorcycleDatabase
import me.proyecto.scalex.data.remote.MotorcycleApiService
import me.proyecto.scalex.data.repository.AuthRepositoryImpl
import me.proyecto.scalex.data.repository.MotorcycleRepositoryImpl
import me.proyecto.scalex.domain.repository.IMotorcycleRepository
import me.proyecto.scalex.domain.repository.IUserRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMotorcycleApiService(retrofit: Retrofit): MotorcycleApiService {
        return retrofit.create(MotorcycleApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMotorcycleDatabase(@ApplicationContext context: Context): MotorcycleDatabase {
        return Room.databaseBuilder(
            context,
            MotorcycleDatabase::class.java,
            "scalex_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMotorcycleDao(database: MotorcycleDatabase): MotorcycleDao {
        return database.motorcycleDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: MotorcycleDatabase): FavoriteDao {
        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideMotorcycleRepository(
        apiService: MotorcycleApiService,
        motorcycleDao: MotorcycleDao
    ): IMotorcycleRepository {
        return MotorcycleRepositoryImpl(apiService, motorcycleDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        favoriteDao: FavoriteDao
    ): IUserRepository {
        return AuthRepositoryImpl(favoriteDao)
    }
}
