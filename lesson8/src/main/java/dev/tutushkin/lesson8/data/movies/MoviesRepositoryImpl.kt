package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.network.NetworkModule.allGenres
import dev.tutushkin.lesson8.data.core.network.NetworkModule.configApi
import dev.tutushkin.lesson8.data.movies.local.MoviesLocalDataSource
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import dev.tutushkin.lesson8.domain.movies.models.*
import dev.tutushkin.lesson8.utils.Util
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

// TODO Move work with all movies data here
// TODO Implement cashing
// TODO Add Result
class MoviesRepositoryImpl(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : MoviesRepository {

    override suspend fun getConfiguration(apiKey: String): Result<Configuration> =
        withContext(ioDispatcher) {
            // TODO Add local configuration load if it exists
//            val localConfiguration = moviesLocalDataSource.getConfiguration()

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
                    println("Config Repo Success!!!")
                }
                .onFailure {
                    println("Config Repo Error!!!")
                }

        }


    override suspend fun getGenres(apiKey: String): Result<List<Genre>> =
        withContext(ioDispatcher) {
            // TODO Add local genres load if it exists

            runCatching {
                moviesRemoteDataSource.getGenres(apiKey)
                    .getOrThrow()
                    .map {
                        Genre(id = it.id, name = it.name)
                    }
            }
                .onSuccess {
                    println("Genres Repo Success!!!")
                }
                .onFailure {
                    println("Genres Repo Error!!!")
                }
        }

    override suspend fun getActors(movieId: Int, apiKey: String): Actor {
        TODO("Not yet implemented")
    }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>> =
        withContext(ioDispatcher) {

            // TODO Add local movies list load if it exists
//        val localMovies = moviesLocalDataSource.getNowPlaying()

//        val localMovies = withContext(Dispatchers.IO) {
//            db.movieDao().getAll()
//        }

//        if (localMovies.isNotEmpty()) {
//            _movies.postValue(localMovies)
//        }

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
                    println("List Repo Success!!!")
                }
                .onFailure {
                    println("List Repo Error!!!")
                }

        }

//            if (remoteMoviesResult is _Result.Success) {
//        val newMovies = remoteMoviesResult.results.map { movie ->
//            MovieEntity(
//                id = movie.id,
//                title = movie.title,
//                overview = movie.overview,
//                poster = "${NetworkModule.imagesBaseUrl}${NetworkModule.posterSize}${movie.posterPath}",
//                backdrop = "",
//                ratings = movie.voteAverage.toFloat(),
//                numberOfRatings = movie.voteCount,
//                minimumAge = if (movie.adult) 18 else 0,
//                year = Util.dateToYear(movie.releaseDate),
//                genres = movie.genreIds
//            )
//        }

//        withContext(Dispatchers.IO) {
//            db.movieDao().insertAll(newMovies)
//        }
//
//        _movies.postValue(newMovies)
//            } else if (remoteMoviesResult is _Result.Error) {
//                _errorMessage.postValue(remoteMoviesResult.message)
//            }


    override suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetails {
        TODO("Not yet implemented")
    }

}