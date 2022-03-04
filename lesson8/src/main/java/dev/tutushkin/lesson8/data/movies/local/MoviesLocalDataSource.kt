package dev.tutushkin.lesson8.data.movies.local

import dev.tutushkin.lesson8.domain.movies.models.MovieWithActors

interface MoviesLocalDataSource {

    suspend fun getConfiguration(): ConfigurationEntity

    suspend fun getGenres(): GenreEntity

    suspend fun getNowPlaying(): List<MovieEntity>

    suspend fun getMovieDetails(id: Int): MovieWithActors

    suspend fun insertAll(movies: List<MovieEntity>)

    suspend fun insert(movie: MovieEntity): Long

    suspend fun deleteAll()
}