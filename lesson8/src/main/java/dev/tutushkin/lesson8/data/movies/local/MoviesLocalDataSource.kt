package dev.tutushkin.lesson8.data.movies.local

interface MoviesLocalDataSource {

    suspend fun getConfiguration(): ConfigurationEntity?

    suspend fun setConfiguration(configuration: ConfigurationEntity)

    suspend fun clearConfiguration()

    suspend fun getGenres(): List<GenreEntity>

    suspend fun setGenres(genres: List<GenreEntity>)

    suspend fun clearGenres()

    suspend fun getNowPlaying(): List<MovieListEntity>

    suspend fun setNowPlaying(movies: List<MovieListEntity>)

    suspend fun clearNowPlaying()

    suspend fun getMovieDetails(id: Int): MovieDetailsEntity?

    suspend fun setMovieDetails(movie: MovieDetailsEntity): Long

    suspend fun clearMovieDetails()

    suspend fun getActors(actorsId: List<Int>): List<ActorEntity>

    suspend fun setActors(actors: List<ActorEntity>)

    suspend fun setActorsLoaded(movieId: Int)

    suspend fun clearActors()
}