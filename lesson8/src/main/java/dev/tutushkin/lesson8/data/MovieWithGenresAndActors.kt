package dev.tutushkin.lesson8.data

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenresAndActors(
    @Embedded
    val movie: Movie,

    @Relation(parentColumn = "genres_id", entityColumn = "id")
    val genres: List<Genre>,

    @Relation(parentColumn = "actors_id", entityColumn = "id")
    val actors: List<Actor>
)
