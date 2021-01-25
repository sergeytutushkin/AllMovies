package dev.tutushkin.lesson8.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.Actor
import dev.tutushkin.lesson8.data.Genre
import dev.tutushkin.lesson8.data.Movie
import dev.tutushkin.lesson8.network.NetworkModule.backdropSize
import dev.tutushkin.lesson8.network.NetworkModule.imagesBaseUrl
import dev.tutushkin.lesson8.network.NetworkModule.profileSize
import dev.tutushkin.lesson8.network.NetworkModule.tmdbApi
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieDetailsViewModel(id: Int) : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    init {
        viewModelScope.launch {
            _movie.postValue(loadMovie(id))
        }
    }

    private suspend fun loadMovie(id: Int): Movie {
        val actorsResponse = tmdbApi.getActors(id, BuildConfig.API_KEY).cast

        val movieResponse = tmdbApi.getMovieDetails(id, BuildConfig.API_KEY)
        return Movie(
            id = movieResponse.id,
            title = movieResponse.title,
            overview = movieResponse.overview,
            poster = "",
            backdrop = "$imagesBaseUrl$backdropSize${movieResponse.backdropPath}",
            ratings = movieResponse.voteAverage.toFloat(),
            numberOfRatings = movieResponse.voteCount,
            minimumAge = if (movieResponse.adult) 18 else 0,
            runtime = movieResponse.runtime,
            genres = movieResponse.genres.map {
                Genre(id = it.id, name = it.name)
            },
            actors = actorsResponse.map {
                Actor(
                    id = it.id,
                    name = it.name,
                    picture = "$imagesBaseUrl$profileSize${it.profilePath}"
                )
            }
        )
    }

}