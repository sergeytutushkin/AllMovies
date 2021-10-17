package dev.tutushkin.lesson8.data.movies.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("results")
    val results: List<MovieDto>,
    @SerialName("total_results")
    val totalResults: Int
)