package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.data.core.network.NetworkModule
import dev.tutushkin.lesson8.data.movies.local.MovieListEntity
import dev.tutushkin.lesson8.data.movies.remote.MovieListDto
import dev.tutushkin.lesson8.domain.movies.models.Genre
import java.text.SimpleDateFormat
import java.util.*

internal fun MovieListDto.toEntity(): MovieListEntity = MovieListEntity(
    id = this.id,
    title = this.title,
    poster = getPosterUrl(this.posterPath),
    ratings = this.voteAverage,
    numberOfRatings = this.voteCount,
    minimumAge = normalizeAge(this.adult),
    year = dateToYear(this.releaseDate),
    genres = filterGenres(this.genreIds)
)

private fun getPosterUrl(posterPath: String?): String =
    "${NetworkModule.configApi.imagesBaseUrl}w342${posterPath}"

private fun normalizeAge(isAdult: Boolean): String = if (isAdult) {
    AGE_ADULT
} else {
    AGE_CHILD
}

private fun dateToYear(value: String): String {
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(value)
    return SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
}

private fun filterGenres(genres: List<Int>): String = NetworkModule.allGenres.filter {
    genres.contains(it.id)
}.joinToString(transform = Genre::name)

private const val AGE_ADULT = "18+"
private const val AGE_CHILD = "13+"