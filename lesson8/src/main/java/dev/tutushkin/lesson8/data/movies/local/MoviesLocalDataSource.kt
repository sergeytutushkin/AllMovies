package dev.tutushkin.lesson8.data.movies.local

interface MoviesLocalDataSource {

    suspend fun getConfiguration(): ConfigurationEntity?

    suspend fun setConfiguration(configuration: ConfigurationEntity)

    suspend fun getGenres(): List<GenreEntity>

    suspend fun setGenres(genres: List<GenreEntity>)

    suspend fun getNowPlaying(): List<MovieListEntity>

    suspend fun setNowPlaying(movies: List<MovieListEntity>)

    suspend fun getMovieDetails(id: Int): MovieDetailsEntity?

    suspend fun setMovieDetails(movie: MovieDetailsEntity): Long

    suspend fun getActors(movieId: Int): List<ActorEntity>

    suspend fun clearNowPlaying()

    suspend fun clearMovieDetails()
}