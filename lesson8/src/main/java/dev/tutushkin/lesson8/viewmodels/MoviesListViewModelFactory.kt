package dev.tutushkin.lesson8.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MoviesListViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
            return MoviesListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}