package dev.tutushkin.lesson8.domain.movies.models

import androidx.room.Embedded
import androidx.room.Relation
import dev.tutushkin.lesson8.data.movies.local.ActorEntity
import dev.tutushkin.lesson8.data.movies.local.MovieEntity

data class MovieWithActors(
    @Embedded
    val movie: MovieEntity,

    @Relation(parentColumn = "actors_id", entityColumn = "id")
    val actors: List<ActorEntity>
)
