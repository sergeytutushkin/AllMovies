package dev.tutushkin.allmovies.ui.movies.viewmodel

import dev.tutushkin.allmovies.domain.movies.models.MovieList

sealed class MoviesState {
    object Loading : MoviesState()
    data class Result(val result: List<MovieList>) : MoviesState()
    data class Error(val e: Throwable) : MoviesState()
}
