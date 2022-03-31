package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.network.NetworkModule
import dev.tutushkin.lesson8.data.core.network.NetworkModule.genres
import dev.tutushkin.lesson8.data.movies.local.MoviesLocalDataSource
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import dev.tutushkin.lesson8.domain.movies.models.*
import dev.tutushkin.lesson8.utils.Result
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

    override suspend fun getConfiguration(apiKey: String): Configuration =
        withContext(ioDispatcher) {
            // TODO Add local configuration load if it exists
//            val localConfiguration = moviesLocalDataSource.getConfiguration()

            val configurationResponse = moviesRemoteDataSource.getConfiguration(apiKey)
            Configuration(imagesBaseUrl = configurationResponse.images.imagesBaseUrl)
        }


    override suspend fun getGenres(apiKey: String): List<Genre> =
        withContext(ioDispatcher) {
            // TODO Add local genres load if it exists

            val genresResponse = moviesRemoteDataSource.getGenres(apiKey)
            genresResponse.genres.map {
                Genre(id = it.id, name = it.name)
            }
        }

    override suspend fun getActors(movieId: Int, apiKey: String): Actor {
        TODO("Not yet implemented")
    }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>, Throwable> =
        withContext(ioDispatcher) {

            // TODO Add local movies list load if it exists
//        val localMovies = moviesLocalDataSource.getNowPlaying()

//        val localMovies = withContext(Dispatchers.IO) {
//            db.movieDao().getAll()
//        }

//        if (localMovies.isNotEmpty()) {
//            _movies.postValue(localMovies)
//        }

            val nowPlayingResponse = moviesRemoteDataSource.getNowPlaying(BuildConfig.API_KEY)
//        val remoteMoviesResult = withContext(Dispatchers.IO) {
//            NetworkModule.moviesApi.getNowPlaying(BuildConfig.API_KEY)
//        }

            Result.Success(result = nowPlayingResponse.results.map { movie ->
                MovieList(
                    id = movie.id,
                    title = movie.title,
                    poster = "${NetworkModule.imagesBaseUrl}${NetworkModule.posterSize}${movie.posterPath}",
                    ratings = movie.voteAverage,
                    numberOfRatings = movie.voteCount,
                    minimumAge = if (movie.adult) 18 else 0,
                    year = Util.dateToYear(movie.releaseDate),
                    genres = genres.filter {
                        movie.genreIds.contains(it.id)
                    }.joinToString(transform = Genre::name)
                )
            })
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
        }

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetails {
        TODO("Not yet implemented")
    }

}