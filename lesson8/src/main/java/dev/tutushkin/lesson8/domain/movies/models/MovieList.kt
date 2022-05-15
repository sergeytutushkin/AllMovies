package dev.tutushkin.lesson8.domain.movies.models

// TODO Delete default value(?)
data class MovieList(
    val id: Int = 0,
    val title: String = "",
    val poster: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int = 0,
    val minimumAge: String = "",    // TODO Correct values
    val year: String = "",
    val genres: String = ""
)
