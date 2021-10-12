package dev.tutushkin.lesson8.data

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity,

    @Relation(parentColumn = "genres_id", entityColumn = "id")
    val genres: List<GenreEntity>
)
