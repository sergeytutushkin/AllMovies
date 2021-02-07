package dev.tutushkin.lesson8.data

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithGenresAndActors(
    @Embedded
    val movie: MovieEntity,

    @Relation(parentColumn = "genres_id", entityColumn = "id")
    val genres: List<GenreEntity>,

    @Relation(parentColumn = "actors_id", entityColumn = "id")
    val actors: List<ActorEntity>
)
