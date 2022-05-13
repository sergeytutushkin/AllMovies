package dev.tutushkin.lesson8.presentation.moviedetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieDetailsViewModelFactory(
    private val repository: MoviesRepository,
    private val id: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(repository, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}