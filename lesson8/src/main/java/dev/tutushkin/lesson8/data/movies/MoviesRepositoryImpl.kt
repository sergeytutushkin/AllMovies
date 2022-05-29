package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.network.NetworkModule.configApi
import dev.tutushkin.lesson8.data.movies.local.*
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import dev.tutushkin.lesson8.domain.movies.models.*
import dev.tutushkin.lesson8.utils.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// TODO Add extensions for mapping

class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MoviesRepository {

    override suspend fun getConfiguration(apiKey: String): Result<Configuration> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearConfiguration()
            val localConfiguration = moviesLocalDataSource.getConfiguration()

            if (localConfiguration != null) {
                Result.success(
                    Configuration(
                        imagesBaseUrl = localConfiguration.imagesBaseUrl,
                        posterSizes = localConfiguration.posterSizes,
                        backdropSizes = localConfiguration.backdropSizes,
                        profileSizes = localConfiguration.profileSizes
                    )
                )
            } else {
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
                        moviesLocalDataSource.setConfiguration(
                            ConfigurationEntity(
                                imagesBaseUrl = it.imagesBaseUrl,
                                posterSizes = it.posterSizes,
                                backdropSizes = it.backdropSizes,
                                profileSizes = it.profileSizes
                            )
                        )
                    }
            }
        }

    override suspend fun getGenres(apiKey: String): Result<List<Genre>> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearGenres()
            val localGenres = moviesLocalDataSource.getGenres()

            if (localGenres.isNotEmpty()) {
                Result.success(localGenres.map {
                    Genre(
                        id = it.id,
                        name = it.name
                    )
                })
            } else {
                runCatching {
                    moviesRemoteDataSource.getGenres(apiKey)
                        .getOrThrow()
                        .map {
                            Genre(id = it.id, name = it.name)
                        }
                }
                    .onSuccess {
                        moviesLocalDataSource.setGenres(it.map { genre ->
                            GenreEntity(id = genre.id, name = genre.name)
                        })
                    }
            }
        }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearNowPlaying()
            var localMovies = moviesLocalDataSource.getNowPlaying()

            if (localMovies.isEmpty()) {
                getFromServer()
                    .onSuccess {
                        moviesLocalDataSource.setNowPlaying(it)
//                        .map { movie ->
//                        MovieListEntity(
//                            id = movie.id,
//                            title = movie.title,
//                            poster = movie.poster,
//                            ratings = movie.ratings,
//                            numberOfRatings = movie.numberOfRatings,
//                            minimumAge = movie.minimumAge,
//                            year = movie.year,
//                            genres = movie.genres
//                        )
                    }
                    .onFailure {
                        return@withContext Result.failure(it)
                    }

                localMovies = moviesLocalDataSource.getNowPlaying()

//                movie ->
//                    MovieList(
//                        id = movie.id,
//                        title = movie.title,
//                        poster = movie.poster,
//                        ratings = movie.ratings,
//                        numberOfRatings = movie.numberOfRatings,
//                        minimumAge = movie.minimumAge,
//                        year = movie.year,
//                        genres = movie.genres
//                    )
//                })
            }

            Result.success(localMovies.map { it.toModel() })

        }

    private suspend fun getFromServer(): Result<List<MovieListEntity>> =
        withContext(ioDispatcher) {
            runCatching {
                moviesRemoteDataSource.getNowPlaying(BuildConfig.API_KEY)
                    .getOrThrow()
                    .map {
                        it.toEntity()
//                            movie ->
//                        MovieList(
//                            id = movie.id,
//                            title = movie.title,
//                            poster = "${configApi.imagesBaseUrl}w342${movie.posterPath}",
//                            ratings = movie.voteAverage,
//                            numberOfRatings = movie.voteCount,
//                            minimumAge = if (movie.adult) "18+" else "0+",
//                            year = Util.dateToYear(movie.releaseDate),
//                            genres = allGenres.filter {
//                                movie.genreIds.contains(it.id)
//                            }.joinToString(transform = Genre::name)
//                        )
                    }
            }

        }

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): Result<MovieDetails> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearMovieDetails()
            val actors = getActors(movieId, apiKey).getOrDefault(listOf())
            val localMovie = moviesLocalDataSource.getMovieDetails(movieId)

            if (localMovie != null) {
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
                        runtime = localMovie.runtime,
                        genres = localMovie.genres,
                        actors = actors
                    )
                )
            } else {
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
                            runtime = movie.runtime,
                            genres = movie.genres.joinToString { it.name },
                            actors = actors
                        )
                    }
                    .onSuccess {
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
                                runtime = it.runtime,
                                genres = it.genres
                            )
                        )
                    }
            }
        }

    override suspend fun getActors(movieId: Int, apiKey: String): Result<List<Actor>> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearActors()
            val localActor = moviesLocalDataSource.getActors(movieId)

            if (localActor.isNotEmpty()) {
                Result.success(
                    localActor.map {
                        Actor(
                            id = it.id,
                            name = it.name,
                            photo = it.photo
                        )
                    })
            } else {
                runCatching {
                    moviesRemoteDataSource.getActors(movieId, apiKey)
                        .getOrThrow()
                        .map {
                            Actor(
                                id = it.id,
                                name = it.name,
                                photo = "${configApi.imagesBaseUrl}w342${it.profilePath}"
                            )
                        }
                }
                    .onSuccess {
                        moviesLocalDataSource.setActors(it.map { actor ->
                            ActorEntity(
                                id = actor.id,
                                name = actor.name,
                                photo = actor.photo
                            )
                        })
                    }
            }
        }

    override suspend fun clearAll() {
        withContext(ioDispatcher) {
            moviesLocalDataSource.clearConfiguration()
            moviesLocalDataSource.clearGenres()
            moviesLocalDataSource.clearNowPlaying()
            moviesLocalDataSource.clearMovieDetails()
            moviesLocalDataSource.clearActors()
        }
    }

}