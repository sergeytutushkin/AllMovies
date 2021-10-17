package dev.tutushkin.lesson8.presentation.movies.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MoviesViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}