package dev.tutushkin.lesson8.data.movies.remote

import dev.tutushkin.lesson8.utils.Result

interface MoviesRemoteDataSource {

    suspend fun getConfiguration(apiKey: String): ConfigurationResponse

    suspend fun getGenres(apiKey: String): GenresResponse

    suspend fun getNowPlaying(apiKey: String): Result<List<MovieListDto>, Throwable>

    suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetailsResponse

    suspend fun getActors(movieId: Int, apiKey: String): MovieActorsResponse
}