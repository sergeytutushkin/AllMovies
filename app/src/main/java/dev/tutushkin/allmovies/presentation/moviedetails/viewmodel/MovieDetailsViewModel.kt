package dev.tutushkin.allmovies.presentation.moviedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.allmovies.BuildConfig
import dev.tutushkin.allmovies.domain.movies.MoviesRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val id: Int
) : ViewModel() {

    private val _currentMovie = MutableLiveData<MovieDetailsState>()
    val currentMovie: LiveData<MovieDetailsState> = _currentMovie

    init {
        viewModelScope.launch {
            _currentMovie.value = handleMovieDetails()
        }
    }

    private suspend fun handleMovieDetails(): MovieDetailsState {
        val movieDetails = moviesRepository.getMovieDetails(id, BuildConfig.API_KEY)

        return if (movieDetails.isSuccess)
            MovieDetailsState.Result(movieDetails.getOrThrow())
        else
            MovieDetailsState.Error(Exception("Error loading movie details from the server!"))
    }

}