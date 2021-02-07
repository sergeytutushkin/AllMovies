package dev.tutushkin.lesson8.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.ActorEntity
import dev.tutushkin.lesson8.data.GenreEntity
import dev.tutushkin.lesson8.data.MovieEntity
import dev.tutushkin.lesson8.network.NetworkModule.backdropSize
import dev.tutushkin.lesson8.network.NetworkModule.imagesBaseUrl
import dev.tutushkin.lesson8.network.NetworkModule.profileSize
import dev.tutushkin.lesson8.network.NetworkModule.tmdbApi
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieDetailsViewModel(id: Int) : ViewModel() {

    private val _movie = MutableLiveData<MovieEntity>()
    val movie: LiveData<MovieEntity> = _movie

    init {
        viewModelScope.launch {
            _movie.postValue(loadMovie(id))
        }
    }

    private suspend fun loadMovie(id: Int): MovieEntity {
        val actorsResponse = tmdbApi.getActors(id, BuildConfig.API_KEY).cast

        val movieResponse = tmdbApi.getMovieDetails(id, BuildConfig.API_KEY)
        return MovieEntity(
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
                GenreEntity(id = it.id, name = it.name)
            },
            actors = actorsResponse.map {
                ActorEntity(
                    id = it.id,
                    name = it.name,
                    photo = "$imagesBaseUrl$profileSize${it.profilePath}"
                )
            }
        )
    }

}