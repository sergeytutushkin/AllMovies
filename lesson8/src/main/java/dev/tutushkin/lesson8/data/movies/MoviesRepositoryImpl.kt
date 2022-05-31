package dev.tutushkin.lesson8.data.movies

import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.movies.local.*
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import dev.tutushkin.lesson8.domain.movies.models.*
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
            var localConfiguration = moviesLocalDataSource.getConfiguration()

            if (localConfiguration == null) {
                getConfigurationFromServer(apiKey)
                    .onSuccess { moviesLocalDataSource.setConfiguration(it) }
                    .onFailure {
                        return@withContext Result.failure(it)
                    }

                localConfiguration = moviesLocalDataSource.getConfiguration()
            }

            if (localConfiguration != null) {
                Result.success(localConfiguration.toModel())
            } else {
                Result.failure(Exception("Configuration cashing error!"))
            }
        }

    private suspend fun getConfigurationFromServer(apiKey: String): Result<ConfigurationEntity> =
        withContext(ioDispatcher) {
            moviesRemoteDataSource.getConfiguration(apiKey)
                .mapCatching { it.toEntity() }
        }

    override suspend fun getGenres(apiKey: String): Result<List<Genre>> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearGenres()
            var localGenres = moviesLocalDataSource.getGenres()

            if (localGenres.isEmpty()) {
                getGenresFromServer(apiKey)
                    .onSuccess { moviesLocalDataSource.setGenres(it) }
                    .onFailure {
                        return@withContext Result.failure(it)
                    }

                localGenres = moviesLocalDataSource.getGenres()
            }

            Result.success(localGenres.map { it.toModel() })
        }

    private suspend fun getGenresFromServer(apiKey: String): Result<List<GenreEntity>> =
        withContext(ioDispatcher) {
            runCatching {
                moviesRemoteDataSource.getGenres(apiKey)
                    .getOrThrow()
                    .map { it.toEntity() }
            }
        }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearNowPlaying()
            var localMovies = moviesLocalDataSource.getNowPlaying()

            if (localMovies.isEmpty()) {
                getNowPlayingFromServer()
                    .onSuccess { moviesLocalDataSource.setNowPlaying(it) }
                    .onFailure {
                        return@withContext Result.failure(it)
                    }

                localMovies = moviesLocalDataSource.getNowPlaying()
            }

            Result.success(localMovies.map { it.toModel() })
        }

    private suspend fun getNowPlayingFromServer(): Result<List<MovieListEntity>> =
        withContext(ioDispatcher) {
            runCatching {
                moviesRemoteDataSource.getNowPlaying(BuildConfig.API_KEY)
                    .getOrThrow()
                    .map { it.toEntity() }
            }
        }

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): Result<MovieDetails> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearMovieDetails()
            val actors = getActors(movieId, apiKey)
            if (actors.isFailure) {
                println("aaa1")
            } else {
                println("aaa1" + actors.getOrThrow())
            }

            var localMovie = moviesLocalDataSource.getMovieDetails(movieId)

            if (localMovie == null) {
                getMovieDetailsFromServer(movieId, apiKey)
                    .onSuccess { moviesLocalDataSource.setMovieDetails(it) }
                    .onFailure {
                        return@withContext Result.failure(it)
                    }

                localMovie = moviesLocalDataSource.getMovieDetails(movieId)
            }

            if (localMovie != null) {
                Result.success(localMovie.toModel(actors.getOrThrow()))
            } else {
                Result.failure(Exception("Movie details cashing error!"))
            }
        }

    private suspend fun getMovieDetailsFromServer(
        movieId: Int,
        apiKey: String
    ): Result<MovieDetailsEntity> =
        withContext(ioDispatcher) {
            moviesRemoteDataSource.getMovieDetails(movieId, apiKey)
                .mapCatching { it.toEntity() }
        }

    override suspend fun getActors(movieId: Int, apiKey: String): Result<List<Actor>> =
        withContext(ioDispatcher) {
//            moviesLocalDataSource.clearActors()
            var localActors = moviesLocalDataSource.getActors(movieId)

            if (localActors.isEmpty()) {
                println("aaa empty")
                getActorsFromServer(movieId, apiKey)
                    .onSuccess {
                        println("aaa success " + it)
                        println("aaa movieId " + movieId)

                        moviesLocalDataSource.setActors(it)
                    }
                    .onFailure {
                        println("aaa failure")
                        return@withContext Result.failure(it)
                    }

                localActors = moviesLocalDataSource.getActors(movieId)
                println("aaa2" + localActors)
            }

            Result.success(localActors.map { it.toModel() })
        }

    private suspend fun getActorsFromServer(
        movieId: Int,
        apiKey: String
    ): Result<List<ActorEntity>> =
        withContext(ioDispatcher) {
            runCatching {
                moviesRemoteDataSource.getActors(movieId, apiKey)
                    .getOrThrow()
                    .map { it.toEntity() }
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