package dev.tutushkin.lesson8.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.data.ActorEntity
import dev.tutushkin.lesson8.data.AppDatabase
import dev.tutushkin.lesson8.data.MovieEntity
import dev.tutushkin.lesson8.data.MovieWithActors
import dev.tutushkin.lesson8.network.NetworkModule.backdropSize
import dev.tutushkin.lesson8.network.NetworkModule.imagesBaseUrl
import dev.tutushkin.lesson8.network.NetworkModule.profileSize
import dev.tutushkin.lesson8.network.NetworkModule.tmdbApi
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

    private val db: AppDatabase = AppDatabase.getDatabase(application)

    init {
        viewModelScope.launch {
            loadMovie(id)
        }
    }

    private suspend fun loadMovie(id: Int) {
        viewModelScope.launch {
            val localMovie = withContext(Dispatchers.IO) {
                db.movieDao().getMovieWithActors(id)
            }

            _currentMovie.postValue(localMovie)

            val remoteMovieResult = withContext(Dispatchers.IO) {
                tmdbApi.getMovieDetails(id, BuildConfig.API_KEY)
            }

            val remoteActorsResult = withContext(Dispatchers.IO) {
                tmdbApi.getActors(id, BuildConfig.API_KEY).cast
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
                db.movieDao().insert(newMovieEntity)
                db.actorDao().insertAll(newActorsEntity)
            }

            _currentMovie.postValue(MovieWithActors(newMovieEntity, newActorsEntity))
        }
    }

}