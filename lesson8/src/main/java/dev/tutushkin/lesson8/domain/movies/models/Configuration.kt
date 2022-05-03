package dev.tutushkin.lesson8.domain.movies.models

data class Configuration(
    val imagesBaseUrl: String = "https://image.tmdb.org/t/p/",
    val posterSizes: List<String> = listOf("w342"),
    val backdropSizes: List<String> = listOf("w780"),
    val profileSizes: List<String> = listOf("w185")
)
