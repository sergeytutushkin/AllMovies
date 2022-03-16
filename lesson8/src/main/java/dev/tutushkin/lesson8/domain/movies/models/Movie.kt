package dev.tutushkin.lesson8.domain.movies.models

data class Movie(
    val id: Long = 0,
    val title: String = "",
//    val overview: String = "",
    val poster: String? = "",
//    val backdrop: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val year: String,
//    val runtime: Int = 0,   // TODO Add?
    val genres: List<Int> = listOf(),
//    val actors: List<Int> = listOf()
)
