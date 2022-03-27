package dev.tutushkin.lesson8.domain.movies.models

data class MovieDetails(
    val id: Long = 0,
    val title: String = "",
    val overview: String = "",
//    val poster: String = "",
    val backdrop: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val year: String,   // TODO Add to screen
    val runtime: Int = 0,
    val genres: String,
    val actors: List<Actor> = listOf()
)
