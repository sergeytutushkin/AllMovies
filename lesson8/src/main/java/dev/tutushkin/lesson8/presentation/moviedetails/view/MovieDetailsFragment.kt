package dev.tutushkin.lesson8.presentation.moviedetails.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.data.core.db.MoviesDb
import dev.tutushkin.lesson8.data.core.network.NetworkModule
import dev.tutushkin.lesson8.data.movies.MoviesRepositoryImpl
import dev.tutushkin.lesson8.data.movies.local.MoviesLocalDataSourceImpl
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSourceImpl
import dev.tutushkin.lesson8.databinding.FragmentMoviesDetailsBinding
import dev.tutushkin.lesson8.presentation.moviedetails.viewmodel.MovieDetailsResult
import dev.tutushkin.lesson8.presentation.moviedetails.viewmodel.MovieDetailsViewModel
import dev.tutushkin.lesson8.presentation.moviedetails.viewmodel.MovieDetailsViewModelFactory
import dev.tutushkin.lesson8.presentation.movies.view.MOVIES_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = MoviesDb.getDatabase(requireActivity().application)
        val remoteDataSource = MoviesRemoteDataSourceImpl(NetworkModule.moviesApi)
        val localDataSource = MoviesLocalDataSourceImpl(
            db.moviesDao(),
            db.movieDetails(),
            db.configurationDao(),
            db.genresDao()
        )
        val repository =
            MoviesRepositoryImpl(remoteDataSource, localDataSource, Dispatchers.Default)
        val arg: Long = arguments?.getLong(MOVIES_KEY) ?: 0
        val viewModel: MovieDetailsViewModel by viewModels {
            MovieDetailsViewModelFactory(
                repository,
                arg
            )
        }

//        val viewModelFactory = MovieDetailsViewModelFactory(requireActivity().application, arg)
//
//        val viewModel =
//            ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        _binding = FragmentMoviesDetailsBinding.bind(view)

//        val poster: ImageView = view.findViewById(R.id.movies_details_poster_image)
//        val age: TextView = view.findViewById(R.id.movies_details_age_text)
//        val title: TextView = view.findViewById(R.id.movies_details_title_text)
//        val genre: TextView = view.findViewById(R.id.movies_details_genres_text)
//        val rating: RatingBar = view.findViewById(R.id.movies_details_rating)
//        val reviews: TextView = view.findViewById(R.id.movies_details_reviews_text)
//        val storyline: TextView = view.findViewById(R.id.movies_details_storyline_content_text)
//        val duration: TextView = view.findViewById(R.id.movies_details_duration_text)
//        val recycler = view.findViewById<RecyclerView>(R.id.movie_details_actors_recycler)

        viewModel.currentMovie.observe(viewLifecycleOwner, ::handleMovieDetails)
        /*viewModel.currentMovie.observe(viewLifecycleOwner, {
            age.text = view.context.getString(R.string.movies_list_age, it.movie.minimumAge)
            title.text = it.movie.title
            genre.text = genres.filter { genreEntity ->
                it.movie.genres.contains(genreEntity.id)
            }.joinToString { genreEntity ->
                genreEntity.name
            }
            rating.rating = it.movie.ratings / 2
            reviews.text = view.context.getString(R.string.movie_details_reviews, it.movie.runtime)
            duration.text = view.context.getString(R.string.movies_list_duration, it.movie.runtime)
            storyline.text = it.movie.overview
            Glide.with(requireContext())
                .load(it.movie.backdrop)
                .into(poster)

            recycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            recycler.adapter = ActorsAdapter(it.actors)
        })*/

        view.findViewById<TextView>(R.id.movies_details_back_text).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

    }

    private fun handleMovieDetails(state: MovieDetailsResult) {
        when (state) {
            is MovieDetailsResult.SuccessResult -> {
//                hideLoading()
                println("Fragment Details Success!!!")
//                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
//                adapter.submitList(state.result)
            }
            is MovieDetailsResult.ErrorResult -> {
//                hideLoading()
                println("Fragment Details Error!!!")
                Toast.makeText(requireContext(), state.e.message, Toast.LENGTH_SHORT).show()
            }
            is MovieDetailsResult.Loading -> //showLoading()
            {
//                showLoading()
                println("Fragment Details Loading!!!")
//                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading() {
        TODO("Not yet implemented")
    }

    private fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
