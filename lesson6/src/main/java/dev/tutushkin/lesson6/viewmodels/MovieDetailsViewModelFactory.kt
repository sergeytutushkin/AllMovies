package dev.tutushkin.lesson6.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tutushkin.lesson6.data.Movie

class MovieDetailsViewModelFactory(
    private val application: Application,
    private val movie: Movie
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(application, movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}