package dev.tutushkin.lesson8.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.network.NetworkModule.allGenres
import dev.tutushkin.lesson8.data.core.network.NetworkModule.configApi
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<MoviesResult>()
    val movies: LiveData<MoviesResult> = _movies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

//    private val configuration : Configuration
//    val configuration: LiveData<Configuration> = _configuration

    init {
        viewModelScope.launch {
//            if (imagesBaseUrl.isEmpty())
//                loadConfiguration()
            handleLoadApiConfiguration()

//                if (genres.isEmpty())
//                    loadGenres()
            handleGenres()

//            loadMovies()
            _movies.postValue(handleMoviesNowPlaying())
        }
    }

    private suspend fun handleLoadApiConfiguration() {
        val conf = moviesRepository.getConfiguration(BuildConfig.API_KEY)

        if (conf.isSuccess) {
            configApi = conf.getOrThrow()
        } else {
            println(conf.exceptionOrNull())
//            _errorMessage.postValue("Something went wrong when getting configuration. Please, check your connection and try again")
        }
    }

    private suspend fun handleGenres() {
        val genres = moviesRepository.getGenres(BuildConfig.API_KEY)

        if (genres.isSuccess) {
            allGenres = genres.getOrThrow()
        } else {
            println(genres.exceptionOrNull())
        }
    }

//    private fun loadMovies() {
//
//        viewModelScope.launch {
//            val localMovies = withContext(Dispatchers.IO) {
//                db.moviesDao().getNowPlaying()
//            }
//
//            if (localMovies.isNotEmpty()) {
//                _movies.postValue(localMovies)
//            }
//
//            val remoteMoviesResult = withContext(Dispatchers.IO) {
//                moviesApi.getNowPlaying(BuildConfig.API_KEY)
//            }
//
////            if (remoteMoviesResult is _Result.Success) {
//            val newMovies = remoteMoviesResult.results.map { movie ->
//                MovieEntity(
//                    id = movie.id,
//                    title = movie.title,
//                    overview = movie.overview,
//                    poster = "$imagesBaseUrl$posterSize${movie.posterPath}",
//                    backdrop = "",
//                    ratings = movie.voteAverage.toFloat(),
//                    numberOfRatings = movie.voteCount,
//                    minimumAge = if (movie.adult) 18 else 0,
//                    year = Util.dateToYear(movie.releaseDate),
//                    genres = movie.genreIds
//                )
//            }
//
//            withContext(Dispatchers.IO) {
//                db.moviesDao().insertAll(newMovies)
//            }
//
//            _movies.postValue(newMovies)
////            } else if (remoteMoviesResult is _Result.Error) {
////                _errorMessage.postValue(remoteMoviesResult.message)
////            }
//        }
//    }

    private suspend fun handleMoviesNowPlaying(): MoviesResult {
        val moviesResult = moviesRepository.getNowPlaying(BuildConfig.API_KEY)

        return if (moviesResult.isSuccess)
            MoviesResult.SuccessResult(moviesResult.getOrThrow())
        else
            MoviesResult.ErrorResult(IllegalArgumentException("Error loading movies from the server!"))
    }
}