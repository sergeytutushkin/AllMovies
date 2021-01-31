package dev.tutushkin.lesson8.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.Genre
import dev.tutushkin.lesson8.data.Movie
import dev.tutushkin.lesson8.data.MovieWithGenres
import dev.tutushkin.lesson8.network.NetworkModule.genres
import dev.tutushkin.lesson8.network.NetworkModule.imagesBaseUrl
import dev.tutushkin.lesson8.network.NetworkModule.posterSize
import dev.tutushkin.lesson8.network.NetworkModule.tmdbApi
import dev.tutushkin.lesson8.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MoviesListViewModel : ViewModel() {

    private val _movies = MutableLiveData<List<MovieWithGenres>>()
    val movies: LiveData<List<MovieWithGenres>> = _movies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            if (imagesBaseUrl.isEmpty())
                loadConfiguration()

            if (genres.isEmpty())
                loadGenres()

            loadMovies()
        }
    }

    private suspend fun loadConfiguration() {
        val configurationResponse = tmdbApi.getConfiguration(BuildConfig.API_KEY).images
        imagesBaseUrl = configurationResponse.imagesBaseUrl
    }

    private suspend fun loadGenres() {
        val genresResponse = tmdbApi.getGenres(BuildConfig.API_KEY).genres
        genres = genresResponse.map {
            Genre(id = it.id, name = it.name)
        }
    }

    private suspend fun loadMovies() {

        val localMovies: List<MovieWithGenres> = withContext(Dispatchers.IO) {
            // TODO Read from DB

            emptyList()
        }

        if (localMovies.isNotEmpty()) {
            _movies.postValue(localMovies)
        }

        val remoteMoviesResult = withContext(Dispatchers.IO) {
            tmdbApi.getNowPlaying(BuildConfig.API_KEY)
        }

        if (remoteMoviesResult is Result.Success) {
            val newMovies = remoteMoviesResult.data.results.map { movie ->
                MovieWithGenres(
                    movie = Movie(
                        id = movie.id,
                        title = movie.title,
                        overview = movie.overview,
                        poster = "$imagesBaseUrl$posterSize${movie.posterPath}",
                        backdrop = "",
                        ratings = movie.voteAverage.toFloat(),
                        numberOfRatings = movie.voteCount,
                        minimumAge = if (movie.adult) 18 else 0,
                        genres = movie.genreIds
                    ),
                    genres = genres.filter {
                        movie.genreIds.contains(it.id)
                    }
                )
            }

            withContext(Dispatchers.IO) {
                // TODO Save to DB
            }

            _movies.postValue(newMovies)
        } else if (remoteMoviesResult is Result.Error) {
            _errorMessage.postValue(remoteMoviesResult.message)
        }
    }
//        val moviesResponse = tmdbApi.getNowPlaying(BuildConfig.API_KEY).results
//
//        return moviesResponse.map { movie ->
//            Movie(
//                id = movie.id,
//                title = movie.title,
//                overview = movie.overview,
//                poster = "$imagesBaseUrl$posterSize${movie.posterPath}",
//                backdrop = "",
//                ratings = movie.voteAverage.toFloat(),
//                numberOfRatings = movie.voteCount,
//                minimumAge = if (movie.adult) 18 else 0,
//                genres = genres.filter {
//                    movie.genreIds.contains(it.id)
//                }
//            )
//        }
}