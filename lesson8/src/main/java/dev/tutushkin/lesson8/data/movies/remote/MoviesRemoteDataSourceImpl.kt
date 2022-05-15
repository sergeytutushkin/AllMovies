package dev.tutushkin.lesson8.data.movies.remote

class MoviesRemoteDataSourceImpl(
    private val moviesApi: MoviesApi
) : MoviesRemoteDataSource {

    override suspend fun getConfiguration(apiKey: String): Result<ConfigurationDto> =
        runCatching {
            moviesApi.getConfiguration(apiKey).images
        }

    override suspend fun getGenres(apiKey: String): Result<List<GenreDto>> =
        runCatching {
            moviesApi.getGenres(apiKey).genres
        }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieListDto>> =
        runCatching {
            moviesApi.getNowPlaying(apiKey).results
        }

    override suspend fun getMovieDetails(
        movieId: Int,
        apiKey: String
    ): Result<MovieDetailsResponse> =
        runCatching {
            moviesApi.getMovieDetails(movieId, apiKey)
        }

    override suspend fun getActors(movieId: Int, apiKey: String): Result<List<MovieActorDto>> =
        runCatching {
            moviesApi.getActors(movieId, apiKey).cast
        }

}