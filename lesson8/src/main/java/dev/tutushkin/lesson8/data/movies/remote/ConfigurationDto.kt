package dev.tutushkin.lesson8.data.movies.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationDto(
    @SerialName("secure_base_url")
    val imagesBaseUrl: String,
    @SerialName("poster_sizes")
    val posterSizes: List<String>,
    @SerialName("backdrop_sizes")
    val backdropSizes: List<String>,
    @SerialName("profile_sizes")
    val profileSizes: List<String>
)