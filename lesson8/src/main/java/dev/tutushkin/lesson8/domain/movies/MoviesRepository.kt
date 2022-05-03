package dev.tutushkin.lesson8.domain.movies

import dev.tutushkin.lesson8.domain.movies.models.*

interface MoviesRepository {

    suspend fun getConfiguration(apiKey: String): Result<Configuration>

    suspend fun getGenres(apiKey: String): Result<List<Genre>>

    suspend fun getActors(movieId: Int, apiKey: String): Actor

    suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>>

    suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetails
}