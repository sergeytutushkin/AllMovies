package dev.tutushkin.lesson6.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tutushkin.lesson6.data.Movie

class MovieDetailsViewModel(application: Application, currentMovie: Movie) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    init {
        _movie.postValue(currentMovie)
    }
}