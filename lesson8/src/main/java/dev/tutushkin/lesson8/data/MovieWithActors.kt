package dev.tutushkin.lesson8.data

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithActors(
    @Embedded
    val movie: MovieEntity,

    @Relation(parentColumn = "actors_id", entityColumn = "id")
    val actors: List<ActorEntity>
)
