package dev.tutushkin.lesson8.presentation.moviedetails.viewmodel

import dev.tutushkin.lesson8.domain.movies.models.MovieList

sealed class MovieDetailsResult {
    object Loading : MovieDetailsResult()
    data class SuccessResult(val result: List<MovieList>) : MovieDetailsResult()
    data class ErrorResult(val e: Throwable) : MovieDetailsResult()
}