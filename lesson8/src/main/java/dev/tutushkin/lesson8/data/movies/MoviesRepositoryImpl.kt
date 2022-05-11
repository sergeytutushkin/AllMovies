package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.network.NetworkModule.allGenres
import dev.tutushkin.lesson8.data.core.network.NetworkModule.configApi
import dev.tutushkin.lesson8.data.movies.local.*
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import dev.tutushkin.lesson8.domain.movies.models.*
import dev.tutushkin.lesson8.utils.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// TODO Move work with all movies data here
// TODO Add Result
class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MoviesRepository {

    override suspend fun getConfiguration(apiKey: String): Result<Configuration> =
        withContext(ioDispatcher) {
            val localConfiguration = moviesLocalDataSource.getConfiguration()

            if (localConfiguration == null) {
                moviesRemoteDataSource.getConfiguration(apiKey)
                    .mapCatching {
                        Configuration(
                            imagesBaseUrl = it.imagesBaseUrl,
                            posterSizes = it.posterSizes,
                            backdropSizes = it.backdropSizes,
                            profileSizes = it.profileSizes
                        )
                    }
                    .onSuccess {
                        println("Config Remote Repo Success!!!")
                        moviesLocalDataSource.setConfiguration(
                            ConfigurationEntity(
                                imagesBaseUrl = it.imagesBaseUrl,
                                posterSizes = it.posterSizes,
                                backdropSizes = it.backdropSizes,
                                profileSizes = it.profileSizes
                            )
                        )
                    }
                    .onFailure {
                        println("Config Remote Repo Error!!!")
                    }
            } else {
                println("Config Local Repo Success!!!")
                Result.success(
                    Configuration(
                        imagesBaseUrl = localConfiguration.imagesBaseUrl,
                        posterSizes = localConfiguration.posterSizes,
                        backdropSizes = localConfiguration.backdropSizes,
                        profileSizes = localConfiguration.profileSizes
                    )
                )
            }
        }


    override suspend fun getGenres(apiKey: String): Result<List<Genre>> =
        withContext(ioDispatcher) {
            val localGenres = moviesLocalDataSource.getGenres()

            if (localGenres.isEmpty()) {

                runCatching {
                    moviesRemoteDataSource.getGenres(apiKey)
                        .getOrThrow()
                        .map {
                            Genre(id = it.id, name = it.name)
                        }
                }
                    .onSuccess {
                        println("Genres Remote Repo Success!!!")
                        moviesLocalDataSource.setGenres(it.map { genre ->
                            GenreEntity(id = genre.id, name = genre.name)
                        })
                    }
                    .onFailure {
                        println("Genres Remote Repo Error!!!")
                    }
            } else {
                println("Genres Local Repo Success!!!")
                Result.success(localGenres.map {
                    Genre(id = it.id, name = it.name)
                })
            }
        }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>> =
        withContext(ioDispatcher) {

            val localMovies = moviesLocalDataSource.getNowPlaying()

            if (localMovies.isEmpty()) {

                runCatching {
                    moviesRemoteDataSource.getNowPlaying(BuildConfig.API_KEY)
                        .getOrThrow()
                        .map { movie ->
                            MovieList(
                                id = movie.id,
                                title = movie.title,
//                            poster = "${configApi.imagesBaseUrl}${configApi.posterSizes.first()}${movie.posterPath}",
                                poster = "${configApi.imagesBaseUrl}w342${movie.posterPath}",
                                ratings = movie.voteAverage,
                                numberOfRatings = movie.voteCount,
                                minimumAge = if (movie.adult) "18+" else "0+",
                                year = Util.dateToYear(movie.releaseDate),
                                genres = allGenres.filter {
                                    movie.genreIds.contains(it.id)
                                }.joinToString(transform = Genre::name)
                            )
                        }
                }
                    .onSuccess {
                        moviesLocalDataSource.setNowPlaying(it.map { movie ->
                            MovieListEntity(
                                id = movie.id,
                                title = movie.title,
                                poster = movie.poster,
                                ratings = movie.ratings,
                                numberOfRatings = movie.numberOfRatings,
                                minimumAge = movie.minimumAge,
                                year = movie.year,
                                genres = movie.genres
                            )
                        })
                        println("List Remote Repo Success!!!")
                    }
                    .onFailure {
                        println("List Remote Repo Error!!!")
                    }
            } else {
                println("List Local Repo Success!!!")
                Result.success(localMovies.map { movie ->
                    MovieList(
                        id = movie.id,
                        title = movie.title,
                        poster = movie.poster,
                        ratings = movie.ratings,
                        numberOfRatings = movie.numberOfRatings,
                        minimumAge = movie.minimumAge,
                        year = movie.year,
                        genres = movie.genres
                    )
                })
            }

        }

    override suspend fun getMovieDetails(movieId: Long, apiKey: String): Result<MovieDetails> =
        withContext(ioDispatcher) {
            val localMovie = moviesLocalDataSource.getMovieDetails(movieId)

            if (localMovie == null) {
                moviesRemoteDataSource.getMovieDetails(movieId, apiKey)
                    .mapCatching { movie ->
                        MovieDetails(
                            id = movie.id,
                            title = movie.title,
                            overview = movie.overview,
                            backdrop = "${configApi.imagesBaseUrl}w342${movie.backdropPath}",
                            ratings = movie.voteAverage,
                            numberOfRatings = movie.voteCount,
                            minimumAge = if (movie.adult) "18+" else "0+",
                            year = Util.dateToYear(movie.releaseDate),
//                            genres = movie.genres.map { it.id }
//                                .joinToString(transform = Genre::name)
                            genres = ""
                        )
                    }
                    .onSuccess {
                        println("Details Remote Repo Success!!!")
                        moviesLocalDataSource.setMovieDetails(
                            MovieDetailsEntity(
                                id = it.id,
                                title = it.title,
                                overview = it.overview,
                                backdrop = it.backdrop,
                                ratings = it.ratings,
                                numberOfRatings = it.numberOfRatings,
                                minimumAge = it.minimumAge,
                                year = it.year,
                                genres = it.genres
                            )
                        )
                    }
                    .onFailure {
                        println("Details Remote Repo Error!!!")
                    }
            } else {
                println("Details Local Repo Success!!!")
                Result.success(
                    MovieDetails(
                        id = localMovie.id,
                        title = localMovie.title,
                        overview = localMovie.overview,
                        backdrop = localMovie.backdrop,
                        ratings = localMovie.ratings,
                        numberOfRatings = localMovie.numberOfRatings,
                        minimumAge = localMovie.minimumAge,
                        year = localMovie.year,
                        genres = localMovie.genres
                    )
                )
            }
        }

    override suspend fun getActors(movieId: Int, apiKey: String): Actor {
        TODO("Not yet implemented")
    }

}