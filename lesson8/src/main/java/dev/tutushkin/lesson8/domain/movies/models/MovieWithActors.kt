package dev.tutushkin.lesson8.domain.movies.models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithActors(
    @Embedded
    val movie: Movie,

    @Relation(parentColumn = "actors_id", entityColumn = "id")
    val actors: List<Actor>
)
