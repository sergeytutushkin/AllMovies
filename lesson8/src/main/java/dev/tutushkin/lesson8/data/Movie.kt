package dev.tutushkin.lesson8.data

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int = 0,
    val genres: List<Genre> = listOf(),
    val actors: List<Actor> = listOf()
)