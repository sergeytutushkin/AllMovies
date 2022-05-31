package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.data.core.network.NetworkModule
import dev.tutushkin.lesson8.data.movies.local.*
import dev.tutushkin.lesson8.data.movies.remote.*
import dev.tutushkin.lesson8.domain.movies.models.Genre
import java.text.SimpleDateFormat
import java.util.*

internal fun MovieListDto.toEntity(): MovieListEntity = MovieListEntity(
    id = this.id,
    title = this.title,
    poster = getImageUrl(this.posterPath),
    ratings = this.voteAverage,
    numberOfRatings = this.voteCount,
    minimumAge = normalizeAge(this.adult),
    year = dateToYear(this.releaseDate),
    genres = filterGenres(this.genreIds)
)

internal fun MovieDetailsResponse.toEntity(): MovieDetailsEntity = MovieDetailsEntity(
    id = this.id,
    title = this.title,
    overview = this.overview,
    backdrop = getImageUrl(this.backdropPath),
    ratings = this.voteAverage,
    numberOfRatings = this.voteCount,
    minimumAge = normalizeAge(this.adult),
    year = dateToYear(this.releaseDate),
    runtime = this.runtime,
    genres = this.genres.joinToString { it.name }
)

internal fun MovieActorDto.toEntity(): ActorEntity = ActorEntity(
    id = this.id,
    name = this.name,
    photo = getImageUrl(this.profilePath)
)

internal fun GenreDto.toEntity(): GenreEntity = GenreEntity(
    id = this.id,
    name = this.name
)

internal fun ConfigurationDto.toEntity(): ConfigurationEntity = ConfigurationEntity(
    imagesBaseUrl = this.imagesBaseUrl,
    posterSizes = this.posterSizes,
    backdropSizes = this.backdropSizes,
    profileSizes = this.profileSizes
)

private fun getImageUrl(posterPath: String?): String =
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