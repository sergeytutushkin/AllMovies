package dev.tutushkin.allmovies.presentation.moviedetails.viewmodel

import dev.tutushkin.allmovies.domain.movies.models.MovieDetails

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Result(val movie: MovieDetails) : MovieDetailsState()
    data class Error(val e: Throwable) : MovieDetailsState()
}