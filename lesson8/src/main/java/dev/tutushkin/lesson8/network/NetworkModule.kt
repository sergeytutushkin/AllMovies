package dev.tutushkin.lesson8.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.tutushkin.lesson8.data.GenreEntity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit

object NetworkModule {

    private val baseUrl = "https://api.themoviedb.org/3/"
    var imagesBaseUrl = ""
    var genres: List<GenreEntity> = listOf()

    // TODO Optimize image sizes dynamically based on a display
    var posterSize = "w342"
    var backdropSize = "w780"
    var profileSize = "w185"

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
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    @ExperimentalSerializationApi
    val tmdbApi: TmdbApi = retrofit.create()
}