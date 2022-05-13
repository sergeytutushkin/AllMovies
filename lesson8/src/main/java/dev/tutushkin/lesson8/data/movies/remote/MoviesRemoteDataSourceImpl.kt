package dev.tutushkin.lesson8.data.movies.remote

class MoviesRemoteDataSourceImpl(
    private val moviesApi: MoviesApi
) : MoviesRemoteDataSource {

    override suspend fun getConfiguration(apiKey: String): Result<ConfigurationDto> =
        runCatching { moviesApi.getConfiguration(apiKey).images }
            .onSuccess {
                println("Config Source Success!!!")
            }
            .onFailure {
                println("Config Source Error!!!")
            }

    override suspend fun getGenres(apiKey: String): Result<List<GenreDto>> =
        runCatching { moviesApi.getGenres(apiKey).genres }
            .onSuccess {
                println("Genres Source Success!!!")
            }
            .onFailure {
                println("Genres Source Error!!!")
            }

    override suspend fun getNowPlaying(apiKey: String): Result<List<MovieListDto>> =
        runCatching { moviesApi.getNowPlaying(apiKey).results }
            .onFailure {
                println("List Source Error!!!")
            }.onSuccess {
                println("List Source Success!!!")
            }


    override suspend fun getMovieDetails(
        movieId: Int,
        apiKey: String
    ): Result<MovieDetailsResponse> =
        runCatching {
            moviesApi.getMovieDetails(movieId, apiKey)
        }
            .onSuccess {
                println("Details Source Success!!!")
            }
            .onFailure {
                println("Details Source Error!!!")
            }

    override suspend fun getActors(movieId: Int, apiKey: String): MovieActorsResponse =
        moviesApi.getActors(movieId, apiKey)
}