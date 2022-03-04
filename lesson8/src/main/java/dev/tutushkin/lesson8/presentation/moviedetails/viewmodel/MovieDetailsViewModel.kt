package dev.tutushkin.lesson8.presentation.moviedetails.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.core.db.MoviesDb
import dev.tutushkin.lesson8.data.core.network.NetworkModule.backdropSize
import dev.tutushkin.lesson8.data.core.network.NetworkModule.imagesBaseUrl
import dev.tutushkin.lesson8.data.core.network.NetworkModule.moviesApi
import dev.tutushkin.lesson8.data.core.network.NetworkModule.profileSize
import dev.tutushkin.lesson8.data.movies.local.ActorEntity
import dev.tutushkin.lesson8.data.movies.local.MovieEntity
import dev.tutushkin.lesson8.domain.movies.models.MovieWithActors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieDetailsViewModel(
    application: Application,
    id: Int
) : ViewModel() {

    private val _currentMovie = MutableLiveData<MovieWithActors>()
    val currentMovie: LiveData<MovieWithActors> = _currentMovie

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val db: MoviesDb = MoviesDb.getDatabase(application)

    init {
        viewModelScope.launch {
            loadMovie(id)
        }
    }

    private suspend fun loadMovie(id: Int) {
        viewModelScope.launch {
            val localMovie = withContext(Dispatchers.IO) {
                db.moviesDao().getMovieDetails(id)
            }

            _currentMovie.postValue(localMovie)

            val remoteMovieResult = withContext(Dispatchers.IO) {
                moviesApi.getMovieDetails(id, BuildConfig.API_KEY)
            }

            val remoteActorsResult = withContext(Dispatchers.IO) {
                moviesApi.getActors(id, BuildConfig.API_KEY).cast
            }

            val newMovieEntity = MovieEntity(
                id = remoteMovieResult.id,
                title = remoteMovieResult.title,
                overview = remoteMovieResult.overview,
                poster = "",
                backdrop = "$imagesBaseUrl$backdropSize${remoteMovieResult.backdropPath}",
                ratings = remoteMovieResult.voteAverage.toFloat(),
                numberOfRatings = remoteMovieResult.voteCount,
                minimumAge = if (remoteMovieResult.adult) 18 else 0,
                runtime = remoteMovieResult.runtime,
                year = "",
                genres = remoteMovieResult.genres.map {
                    it.id
                },
                actors = remoteActorsResult.map {
                    it.id
                }
            )

            val newActorsEntity = remoteActorsResult.map {
                ActorEntity(
                    id = it.id,
                    name = it.name,
                    photo = "$imagesBaseUrl$profileSize${it.profilePath}"
                )
            }

            withContext(Dispatchers.IO) {
                db.moviesDao().insert(newMovieEntity)
                db.actorsDao().insertAll(newActorsEntity)
            }

            _currentMovie.postValue(MovieWithActors(newMovieEntity, newActorsEntity))
        }
    }

}