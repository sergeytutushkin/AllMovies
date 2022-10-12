package dev.tutushkin.allmovies.data.movies

import dev.tutushkin.allmovies.BuildConfig
import dev.tutushkin.allmovies.data.movies.local.*
import dev.tutushkin.allmovies.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.allmovies.domain.movies.MoviesRepository
import dev.tutushkin.allmovies.domain.movies.models.*
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {

    override suspend fun getConfiguration(apiKey: String): Result<Configuration> {
//            moviesLocalDataSource.clearConfiguration()
        var localConfiguration = moviesLocalDataSource.getConfiguration()

        if (localConfiguration == null) {
            getConfigurationFromServer(apiKey)
                .onSuccess { moviesLocalDataSource.setConfiguration(it) }
                .onFailure {
                    return Result.failure(it)
                }

            localConfiguration = moviesLocalDataSource.getConfiguration()
        }

        return if (localConfiguration != null) {
            Result.success(localConfiguration.toModel())
        } else {
            Result.failure(Exception("Configuration cashing error!"))
        }
    }

    private suspend fun getConfigurationFromServer(apiKey: String): Result<ConfigurationEntity> =
        moviesRemoteDataSource.getConfiguration(apiKey)
            .mapCatching { it.toEntity() }

    override suspend fun getGenres(apiKey: String): Result<List<Genre>> {
//            moviesLocalDataSource.clearGenres()
        var localGenres = moviesLocalDataSource.getGenres()

        if (localGenres.isEmpty()) {
            getGenresFromServer(apiKey)
                .onSuccess { moviesLocalDataSource.setGenres(it) }
                .onFailure {
                    return Result.failure(it)
                }

            localGenres = moviesLocalDataSource.getGenres()
        }

        return if (localGenres.isNotEmpty()) {
            Result.success(localGenres.map { it.toModel() })
        } else {
            Result.failure(Exception("Movies list cashing error!"))
        }
    }

    private suspend fun getGenresFromServer(apiKey: String): Result<List<GenreEntity>> =
        runCatching {
            moviesRemoteDataSource.getGenres(apiKey)
                .getOrThrow()
                .map { it.toEntity() }
        }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieList>> {
//            moviesLocalDataSource.clearNowPlaying()
        var localMovies = moviesLocalDataSource.getNowPlaying()

        if (localMovies.isEmpty()) {
            getNowPlayingFromServer()
                .onSuccess { moviesLocalDataSource.setNowPlaying(it) }
                .onFailure {
                    return Result.failure(it)
                }

            localMovies = moviesLocalDataSource.getNowPlaying()
        }

        return if (localMovies.isNotEmpty()) {
            Result.success(localMovies.map { it.toModel() })
        } else {
            Result.failure(Exception("Movies list cashing error!"))
        }
    }

    private suspend fun getNowPlayingFromServer(): Result<List<MovieListEntity>> =
        runCatching {
            moviesRemoteDataSource.getNowPlaying(BuildConfig.API_KEY)
                .getOrThrow()
                .map { it.toEntity() }
        }

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): Result<MovieDetails> {
//            moviesLocalDataSource.clearMovieDetails()
        var localMovie = moviesLocalDataSource.getMovieDetails(movieId)

        if (localMovie == null) {
            getMovieDetailsFromServer(movieId, apiKey)
                .onSuccess {
                    it.actors = getActorsList(movieId, apiKey).getOrThrow()
                    moviesLocalDataSource.setMovieDetails(it)
                }
                .onFailure {
                    return Result.failure(it)
                }

            localMovie = moviesLocalDataSource.getMovieDetails(movieId)
        }

        return if (localMovie != null) {
            val actors = getActorsData(localMovie, apiKey).getOrThrow()
            Result.success(localMovie.toModel(actors))
        } else {
            Result.failure(Exception("Movie details cashing error!"))
        }
    }

    private suspend fun getMovieDetailsFromServer(
        movieId: Int,
        apiKey: String
    ): Result<MovieDetailsEntity> =
        moviesRemoteDataSource.getMovieDetails(movieId, apiKey)
            .mapCatching { it.toEntity() }

    private suspend fun getActorsList(movieId: Int, apiKey: String): Result<List<Int>> =
        getActorsFromServer(movieId, apiKey).mapCatching {
            it.map { actor ->
                actor.id
            }
        }

    private suspend fun getActorsData(
        movie: MovieDetailsEntity,
        apiKey: String
    ): Result<List<Actor>> {
//            moviesLocalDataSource.clearActors()
        if (!movie.isActorsLoaded) {
            getActorsFromServer(movie.id, apiKey)
                .onSuccess {
                    moviesLocalDataSource.setActors(it)
                    moviesLocalDataSource.setActorsLoaded(movie.id)
                }
                .onFailure {
                    return Result.failure(it)
                }
        }

        val localActors = moviesLocalDataSource.getActors(movie.actors)

        return if (localActors.isNotEmpty()) {
            Result.success(localActors.map { it.toModel() })
        } else {
            Result.failure(Exception("Actors cashing error!"))
        }
    }

    private suspend fun getActorsFromServer(
        movieId: Int,
        apiKey: String
    ): Result<List<ActorEntity>> =
        runCatching {
            moviesRemoteDataSource.getActors(movieId, apiKey)
                .getOrThrow()
                .map { it.toEntity() }
        }

    override suspend fun clearAll() {
        moviesLocalDataSource.clearConfiguration()
        moviesLocalDataSource.clearGenres()
        moviesLocalDataSource.clearNowPlaying()
        moviesLocalDataSource.clearMovieDetails()
        moviesLocalDataSource.clearActors()
    }

}
