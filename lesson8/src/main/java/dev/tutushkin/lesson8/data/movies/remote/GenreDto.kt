package dev.tutushkin.lesson8.data.movies.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: Int
)