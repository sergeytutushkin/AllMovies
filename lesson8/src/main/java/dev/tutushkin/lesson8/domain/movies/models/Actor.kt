package dev.tutushkin.lesson8.domain.movies.models

data class Actor(
    val id: Int,
    val name: String,
    val profilePath: String? = null
)
