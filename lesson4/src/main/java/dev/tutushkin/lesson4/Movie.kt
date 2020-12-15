package dev.tutushkin.lesson4

data class Movie(
    val title: String,
    val poster: Int,
    val genres: String,
    val rating: Float,
    val reviews: Int,
    val duration: Int,
    val age: String,
    val like: Boolean,
    val storyline: String,
    val actors: List<Actor>?
)
