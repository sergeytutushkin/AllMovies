package dev.tutushkin.lesson8.presentation.moviedetails.viewmodel

import dev.tutushkin.lesson8.domain.movies.models.MovieDetails

sealed class MovieDetailsResult {
    object Loading : MovieDetailsResult()
    data class SuccessResult(val result: MovieDetails) : MovieDetailsResult()
    data class ErrorResult(val e: Throwable) : MovieDetailsResult()
}