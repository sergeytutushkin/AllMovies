package dev.tutushkin.lesson8.data.movies.remote

class MoviesRemoteDataSourceImpl(
    private val moviesApi: MoviesApi
) : MoviesRemoteDataSource {

    override suspend fun getConfiguration(apiKey: String): ConfigurationResponse =
        moviesApi.getConfiguration(apiKey)

    override suspend fun getGenres(apiKey: String): GenresResponse =
        moviesApi.getGenres(apiKey)

    override suspend fun getNowPlaying(apiKey: String): MovieListResponse =
        moviesApi.getNowPlaying(apiKey)

    override suspend fun getMovieDetails(movieId: Int, apiKey: String): MovieDetailsResponse =
        moviesApi.getMovieDetails(movieId, apiKey)

    override suspend fun getActors(movieId: Int, apiKey: String): MovieActorsResponse =
        moviesApi.getActors(movieId, apiKey)
}