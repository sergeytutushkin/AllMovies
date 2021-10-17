package dev.tutushkin.lesson8.data.movies.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    @SerialName("images")
    val images: ConfigurationDto
)