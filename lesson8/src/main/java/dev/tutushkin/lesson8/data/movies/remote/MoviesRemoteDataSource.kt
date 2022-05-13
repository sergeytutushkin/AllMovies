package dev.tutushkin.lesson8.data.movies.remote

interface MoviesRemoteDataSource {

    suspend fun getConfiguration(apiKey: String): Result<ConfigurationDto>

    suspend fun getGenres(apiKey: String): Result<List<GenreDto>>

    suspend fun getNowPlaying(apiKey: String): Result<List<MovieListDto>>

    suspend fun getMovieDetails(movieId: Int, apiKey: String): Result<MovieDetailsResponse>

    suspend fun getActors(movieId: Int, apiKey: String): MovieActorsResponse
}