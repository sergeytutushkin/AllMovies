package dev.tutushkin.lesson8.domain.movies.models

import androidx.room.Embedded
import androidx.room.Relation
import dev.tutushkin.lesson8.data.movies.local.GenreEntity
import dev.tutushkin.lesson8.data.movies.local.MovieEntity

data class MovieWithGenres(
    @Embedded
    val movie: MovieEntity,

    @Relation(parentColumn = "genres_id", entityColumn = "id")
    val genres: List<GenreEntity>
)
