package dev.tutushkin.lesson8.domain.movies.models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenres(
    @Embedded
    val movie: Movie,

    @Relation(parentColumn = "genres_id", entityColumn = "id")
    val genres: List<Genre>
)
