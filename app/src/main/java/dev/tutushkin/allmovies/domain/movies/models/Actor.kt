package dev.tutushkin.allmovies.domain.movies.models

data class Actor(
    val id: Int,
    val name: String,
    val photo: String? = null
)
