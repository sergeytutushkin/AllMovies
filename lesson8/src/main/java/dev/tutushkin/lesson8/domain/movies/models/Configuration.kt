package dev.tutushkin.lesson8.domain.movies.models

data class Configuration(
    val imagesBaseUrl: String = "",
    val posterSize: String = "w342",
    val backdropSize: String = "w780",
    val profileSize: String = "w185"
)
