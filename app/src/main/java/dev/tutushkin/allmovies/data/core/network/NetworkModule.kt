package dev.tutushkin.allmovies.data.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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

// TODO Implement Api Key through the interceptor
// TODO Get off singleton
object NetworkModule {

    var allGenres: List<Genre> = listOf()

    // TODO Move to DataStore(?)
    var configApi: Configuration = Configuration()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient().newBuilder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    private val contentType = "application/json".toMediaType()

    @ExperimentalSerializationApi
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    @ExperimentalSerializationApi
    val moviesApi: MoviesApi = retrofit.create()
}