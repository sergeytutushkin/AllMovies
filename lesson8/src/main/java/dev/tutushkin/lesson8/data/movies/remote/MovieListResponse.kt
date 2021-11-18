package dev.tutushkin.lesson8.data.movies.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse(
    @SerialName("results")
    val results: List<MovieListDto>,
    @SerialName("total_results")
    val totalResults: Int
)