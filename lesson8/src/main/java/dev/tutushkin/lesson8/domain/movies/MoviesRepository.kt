package dev.tutushkin.lesson8.domain.movies

import dev.tutushkin.lesson8.domain.movies.models.*
import dev.tutushkin.lesson8.utils.Result

interface MoviesRepository {

    suspend fun getConfiguration(apiKey: String): Configuration

    suspend fun getGenres(apiKey: String): List<Genre>

    suspend fun getActors(movieId: Int, apiKey: String): Actor

    suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>, Throwable>

    suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetails
}