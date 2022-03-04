package dev.tutushkin.lesson8.data.movies.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "configuration")
data class ConfigurationEntity(
    @ColumnInfo(name = "image_url")
    val imagesBaseUrl: String,

    @ColumnInfo(name = "poster_sizes")
    val posterSizes: List<String>,

    @ColumnInfo(name = "backdrop_sizes")
    val backdropSizes: List<String>,

    @ColumnInfo(name = "profile_sizes")
    val profileSizes: List<String>
)
