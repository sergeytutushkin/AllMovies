package dev.tutushkin.lesson8.presentation.movies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.network.NetworkModule.configApi
import dev.tutushkin.lesson8.data.core.network.NetworkModule.genres
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import dev.tutushkin.lesson8.utils.Result
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<MoviesResult>>()
    val movies: LiveData<List<MoviesResult>> = _movies

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

//    private val configuration : Configuration
//    val configuration: LiveData<Configuration> = _configuration

//    private val db: MoviesDb = MoviesDb.getDatabase(application)

    init {
        viewModelScope.launch {
//            if (imagesBaseUrl.isEmpty())
//                loadConfiguration()
            configApi = moviesRepository.getConfiguration(BuildConfig.API_KEY)

//                if (genres.isEmpty())
//                    loadGenres()
            genres = moviesRepository.getGenres(BuildConfig.API_KEY)

//            loadMovies()
            _movies.postValue(moviesRepository.getNowPlaying(BuildConfig.API_KEY))
        }
    }

//    private fun loadConfiguration() {
//        viewModelScope.launch {
//            val configurationResponse = tmdbApi.getConfiguration(BuildConfig.API_KEY).images
//            imagesBaseUrl = configurationResponse.imagesBaseUrl
//        }
//    }

//    private fun loadGenres() {
//        viewModelScope.launch {
//            val genresResponse = moviesApi.getGenres(BuildConfig.API_KEY).genres
//            genres = genresResponse.map {
//                GenreEntity(id = it.id, name = it.name)
//            }
//        }
//    }

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

    private suspend fun handleMoviesList(): MoviesResult {
        return when (val moviesResult = moviesRepository.getNowPlaying(BuildConfig.API_KEY)) {
            is Result.Error -> MoviesResult.ErrorResult(IllegalArgumentException("Error loading movies from the server!"))
            is Result.Success -> MoviesResult.SuccessResult(moviesResult.result)
        }
    }
}