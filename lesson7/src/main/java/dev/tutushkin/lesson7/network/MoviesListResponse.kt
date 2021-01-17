package dev.tutushkin.lesson7.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesListResponse(
    @SerialName("results")
    val results: List<ResultsItem>,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class ResultsItem(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("genre_ids")
    val genreIds: List<Int>
)
