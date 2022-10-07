package dev.tutushkin.allmovies.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tutushkin.allmovies.BuildConfig
import dev.tutushkin.allmovies.data.movies.remote.MoviesApi
import dev.tutushkin.allmovies.domain.movies.models.Configuration
import dev.tutushkin.allmovies.domain.movies.models.Genre
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// TODO Implement Api Key through the interceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // TODO Move genre and config variables to another module
    var allGenres: List<Genre> = listOf()

    // TODO Move to DataStore(?)
    var configApi: Configuration = Configuration()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    @ExperimentalSerializationApi
    fun provideRetrofit(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Singleton
    @Provides
    @ExperimentalSerializationApi
    fun provideMoviesApi(
        retrofit: Retrofit
    ): MoviesApi = retrofit.create()
}
