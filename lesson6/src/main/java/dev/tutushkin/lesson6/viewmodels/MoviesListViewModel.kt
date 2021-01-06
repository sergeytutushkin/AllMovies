package dev.tutushkin.lesson6.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson6.data.Movie
import dev.tutushkin.lesson6.data.loadMovies
import kotlinx.coroutines.launch

class MoviesListViewModel(application: Application) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    init {
        viewModelScope.launch {
            _movies.postValue(loadMovies(application))
        }
    }

}