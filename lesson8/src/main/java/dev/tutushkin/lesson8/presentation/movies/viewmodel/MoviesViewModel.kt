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

//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
            handleLoadApiConfiguration()
            handleGenres()
            _movies.postValue(handleMoviesNowPlaying())
        }
    }

    private suspend fun handleLoadApiConfiguration() {
        val conf = moviesRepository.getConfiguration(BuildConfig.API_KEY)

        if (conf.isSuccess) {
            configApi = conf.getOrThrow()
        } else {
            println(conf.exceptionOrNull())
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

    private suspend fun handleMoviesNowPlaying(): MoviesResult {
        val moviesResult = moviesRepository.getNowPlaying(BuildConfig.API_KEY)

        return if (moviesResult.isSuccess)
            MoviesResult.SuccessResult(moviesResult.getOrThrow())
        else
            MoviesResult.ErrorResult(IllegalArgumentException("Error loading movies from the server!"))
    }
}