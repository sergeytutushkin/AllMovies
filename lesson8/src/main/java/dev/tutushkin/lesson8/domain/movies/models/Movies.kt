package dev.tutushkin.lesson8.domain.movies.models

data class Movies(
    val id: Long = 0,
    val title: String = "",
    val overview: String = "",
    val poster: String = "",
    val backdrop: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val year: String,
    val runtime: Int = 0,
    val genres: List<Int> = listOf(),
    val actors: List<Int> = listOf()
)
