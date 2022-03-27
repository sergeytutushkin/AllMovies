package dev.tutushkin.lesson8.presentation.movies.viewmodel

import dev.tutushkin.lesson8.domain.movies.models.MovieList

sealed class MoviesResult {
    object Loading : MoviesResult()
    data class SuccessResult(val result: List<MovieList>) : MoviesResult()
    data class ErrorResult(val e: Throwable) : MoviesResult()
}