package dev.tutushkin.lesson8.data.movies.local

class MoviesLocalDataSourceImpl(
    private val moviesDao: MoviesDao,
    private val movieDetailsDao: MovieDetailsDao,
    private val actorsDao: ActorsDao,
    private val configurationDao: ConfigurationDao,
    private val genresDao: GenresDao
) : MoviesLocalDataSource {

    override suspend fun getConfiguration(): ConfigurationEntity? =
        configurationDao.get()

    override suspend fun setConfiguration(configuration: ConfigurationEntity) {
        configurationDao.insert(configuration)
    }

    override suspend fun getGenres(): List<GenreEntity> =
        genresDao.getAll()

    override suspend fun setGenres(genres: List<GenreEntity>) {
        genresDao.insertAll(genres)
    }

    override suspend fun getNowPlaying(): List<MovieListEntity> =
        moviesDao.getNowPlaying()

    override suspend fun setNowPlaying(movies: List<MovieListEntity>) {
        moviesDao.insertAll(movies)
    }

    override suspend fun getMovieDetails(id: Int): MovieDetailsEntity? =
        movieDetailsDao.getMovieDetails(id)

    override suspend fun setMovieDetails(movie: MovieDetailsEntity): Long =
        movieDetailsDao.insert(movie)

    override suspend fun getActors(movieId: Int): List<ActorEntity> =
        actorsDao.getActors(movieId)

    override suspend fun clearNowPlaying() {
        moviesDao.clear()
    }

    override suspend fun clearMovieDetails() {
        movieDetailsDao.clear()
    }
}