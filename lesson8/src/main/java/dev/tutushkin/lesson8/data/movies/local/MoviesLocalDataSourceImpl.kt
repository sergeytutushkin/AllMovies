package dev.tutushkin.lesson8.data.movies.local

import dev.tutushkin.lesson8.domain.movies.models.MovieWithActors

class MoviesLocalDataSourceImpl(
    private val moviesDao: MoviesDao,
    private val configurationDao: ConfigurationDao
) : MoviesLocalDataSource {

    override suspend fun getConfiguration(): ConfigurationEntity =
        configurationDao.get()

    override suspend fun getGenres(): GenreEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getNowPlaying(): List<MovieEntity> =
        moviesDao.getNowPlaying()

    override suspend fun getMovieDetails(id: Int): MovieWithActors =
        moviesDao.getMovieDetails(id)

    override suspend fun insert(movie: MovieEntity): Long =
        moviesDao.insert(movie)

    override suspend fun insertAll(movies: List<MovieEntity>) {
        moviesDao.insertAll(movies)
    }

    override suspend fun deleteAll() {
        moviesDao.deleteAll()
    }
}