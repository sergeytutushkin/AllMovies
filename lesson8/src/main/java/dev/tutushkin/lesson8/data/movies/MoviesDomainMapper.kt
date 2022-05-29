package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.data.movies.local.MovieListEntity
import dev.tutushkin.lesson8.domain.movies.models.MovieList

internal fun MovieListEntity.toModel(): MovieList = MovieList(
    id = this.id,
    title = this.title,
    poster = this.poster,
    ratings = this.ratings,
    numberOfRatings = this.numberOfRatings,
    minimumAge = this.minimumAge,
    year = this.year,
    genres = this.genres
)