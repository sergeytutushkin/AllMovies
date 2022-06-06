package dev.tutushkin.allmovies.data.movies.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieActorsResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("cast")
    val cast: List<MovieActorDto>
)