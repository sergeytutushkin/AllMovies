package dev.tutushkin.lesson8.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieActorsResponse(
    @SerialName("cast")
    val cast: List<CastItem>,
    @SerialName("id")
    val id: Int
)

@Serializable
data class CastItem(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("profile_path")
    val profilePath: String? = null
)
