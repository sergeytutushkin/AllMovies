package dev.tutushkin.lesson8.presentation.moviedetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tutushkin.lesson8.BuildConfig
import dev.tutushkin.lesson8.domain.movies.MoviesRepository
import kotlinx.coroutines.launch

//@ExperimentalSerializationApi
class MovieDetailsViewModel(
    private val moviesRepository: MoviesRepository,
    private val id: Long
) : ViewModel() {

    private val _currentMovie = MutableLiveData<MovieDetailsState>()
    val currentMovie: LiveData<MovieDetailsState> = _currentMovie

//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage

    init {
        viewModelScope.launch {
//            loadMovie(id)

            _currentMovie.value = handleMovieDetails()
        }
    }

    private suspend fun loadMovie(id: Int) {
        /*viewModelScope.launch {
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

            _currentMovie.postValue(MovieDetailsResult(newMovieEntity, newActorsEntity))
        }*/
    }

    private suspend fun handleMovieDetails(): MovieDetailsState {
        val movieDetails = moviesRepository.getMovieDetails(id, BuildConfig.API_KEY)

        return if (movieDetails.isSuccess)
            MovieDetailsState.Result(movieDetails.getOrThrow())
        else
            MovieDetailsState.Error(IllegalArgumentException("Error loading movie details from the server!"))
    }

}