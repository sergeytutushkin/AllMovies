package dev.tutushkin.lesson8.presentation.moviedetails.viewmodel

import dev.tutushkin.lesson8.domain.movies.models.MovieDetails

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Result(val movie: MovieDetails) : MovieDetailsState()
    data class Error(val e: Throwable) : MovieDetailsState()
}