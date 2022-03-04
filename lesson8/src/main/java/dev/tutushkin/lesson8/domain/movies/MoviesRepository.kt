package dev.tutushkin.lesson8.domain.movies

import dev.tutushkin.lesson8.domain.movies.models.*

interface MoviesRepository {

    suspend fun getConfiguration(apiKey: String): Configuration

    suspend fun getGenres(apiKey: String): List<Genre>

    suspend fun getActors(movieId: Int, apiKey: String): Actor

    suspend fun getNowPlaying(apiKey: String): List<MovieWithGenres>

    suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetails
}