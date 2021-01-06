package dev.tutushkin.lesson7.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tutushkin.lesson7.data.Movie

class MovieDetailsViewModel(application: Application, currentMovie: Movie) : ViewModel() {

    private val _movie = MutableLiveData(currentMovie)
    val movie: LiveData<Movie> = _movie

}