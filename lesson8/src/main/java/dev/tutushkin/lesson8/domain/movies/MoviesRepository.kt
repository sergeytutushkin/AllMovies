package dev.tutushkin.lesson8.domain.movies

import dev.tutushkin.lesson8.domain.movies.models.Configuration
import dev.tutushkin.lesson8.domain.movies.models.Genre
import dev.tutushkin.lesson8.domain.movies.models.MovieDetails
import dev.tutushkin.lesson8.domain.movies.models.MovieList

interface MoviesRepository {

    suspend fun getConfiguration(apiKey: String): Result<Configuration>

    suspend fun getGenres(apiKey: String): Result<List<Genre>>

    suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>>

    suspend fun getMovieDetails(movieId: Int, apiKey: String): Result<MovieDetails>

    suspend fun clearAll()
}