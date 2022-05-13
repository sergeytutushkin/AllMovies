package dev.tutushkin.lesson8.domain.movies.models

// TODO Delete default value(?)
data class MovieList(
    val id: Int = 0,
    val title: String = "",
//    val overview: String = "",
    val poster: String = "",
//    val backdrop: String = "",
    val ratings: Float = 0.0f,
    val numberOfRatings: Int = 0,
    val minimumAge: String = "",    // TODO Correct values
    val year: String = "",
    val genres: String = ""
//    val runtime: Int = 0,   // TODO Add?
//    val genres: List<Int> = listOf(),
//    val actors: List<Int> = listOf()
)
