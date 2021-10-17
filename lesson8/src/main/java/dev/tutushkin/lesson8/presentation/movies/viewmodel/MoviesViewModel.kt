package dev.tutushkin.lesson8.presentation.movies.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.db.MoviesDb
import dev.tutushkin.lesson8.data.core.network.NetworkModule.genres
import dev.tutushkin.lesson8.data.core.network.NetworkModule.imagesBaseUrl
import dev.tutushkin.lesson8.data.core.network.NetworkModule.posterSize
import dev.tutushkin.lesson8.data.core.network.NetworkModule.tmdbApi
import dev.tutushkin.lesson8.data.movies.local.GenreEntity
import dev.tutushkin.lesson8.data.movies.local.MovieEntity
import dev.tutushkin.lesson8.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MoviesViewModel(application: Application) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieEntity>>()
    val movies: LiveData<List<MovieEntity>> = _movies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val db: MoviesDb = MoviesDb.getDatabase(application)

    init {
        viewModelScope.launch {
            if (imagesBaseUrl.isEmpty())
                loadConfiguration()

            if (genres.isEmpty())
                loadGenres()

            loadMovies()
        }
    }

    private fun loadConfiguration() {
        viewModelScope.launch {
            val configurationResponse = tmdbApi.getConfiguration(BuildConfig.API_KEY).images
            imagesBaseUrl = configurationResponse.imagesBaseUrl
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            val genresResponse = tmdbApi.getGenres(BuildConfig.API_KEY).genres
            genres = genresResponse.map {
                GenreEntity(id = it.id, name = it.name)
            }
        }
    }

    private fun loadMovies() {

        viewModelScope.launch {
            val localMovies = withContext(Dispatchers.IO) {
                db.movieDao().getAll()
            }

            if (localMovies.isNotEmpty()) {
                _movies.postValue(localMovies)
            }

            val remoteMoviesResult = withContext(Dispatchers.IO) {
                tmdbApi.getNowPlaying(BuildConfig.API_KEY)
            }

//            if (remoteMoviesResult is _Result.Success) {
            val newMovies = remoteMoviesResult.results.map { movie ->
                MovieEntity(
                    id = movie.id,
                    title = movie.title,
                    overview = movie.overview,
                    poster = "$imagesBaseUrl$posterSize${movie.posterPath}",
                    backdrop = "",
                    ratings = movie.voteAverage.toFloat(),
                    numberOfRatings = movie.voteCount,
                    minimumAge = if (movie.adult) 18 else 0,
                    year = Util.dateToYear(movie.releaseDate),
                    genres = movie.genreIds
                )
            }

            withContext(Dispatchers.IO) {
                db.movieDao().insertAll(newMovies)
            }

            _movies.postValue(newMovies)
//            } else if (remoteMoviesResult is _Result.Error) {
//                _errorMessage.postValue(remoteMoviesResult.message)
//            }
        }
    }
}