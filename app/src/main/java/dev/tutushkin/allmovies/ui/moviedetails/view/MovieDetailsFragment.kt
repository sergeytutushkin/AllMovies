package dev.tutushkin.allmovies.ui.moviedetails.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.tutushkin.allmovies.R
import dev.tutushkin.allmovies.databinding.FragmentMoviesDetailsBinding
import dev.tutushkin.allmovies.domain.movies.models.MovieDetails
import dev.tutushkin.allmovies.ui.moviedetails.viewmodel.MovieDetailsState
import dev.tutushkin.allmovies.ui.moviedetails.viewmodel.MovieDetailsViewModel

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    private val viewModel: MovieDetailsViewModel by viewModels()

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesDetailsBinding.bind(view)

        viewModel.currentMovie.observe(viewLifecycleOwner, ::render)

        binding?.moviesDetailsBackText?.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun render(state: MovieDetailsState) {
        when (state) {
            is MovieDetailsState.Result -> {
//                hideLoading()
                renderResult(state.movie)
//                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
//                adapter.submitList(state.result)
            }
            is MovieDetailsState.Error -> {
//                hideLoading()
                Toast.makeText(requireContext(), state.e.message, Toast.LENGTH_SHORT).show()
            }
            is MovieDetailsState.Loading -> {
//                showLoading()
//                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun renderResult(movie: MovieDetails) {
        binding?.apply {
            moviesDetailsAgeText.text = movie.minimumAge
            moviesDetailsTitleText.text = movie.title
            moviesDetailsGenresText.text = movie.genres
            moviesDetailsRating.rating = movie.ratings / 2
            moviesDetailsRatingsCountText.text =
                requireContext().getString(R.string.movie_details_reviews, movie.numberOfRatings)
            moviesDetailsDurationText.text =
                requireContext().getString(R.string.movies_list_duration, movie.runtime)
            moviesDetailsStorylineContentText.text = movie.overview
            Glide.with(requireContext())
                .load(movie.backdrop)
                .into(moviesDetailsPosterImage)
            movieDetailsActorsRecycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            movieDetailsActorsRecycler.adapter = ActorsAdapter(movie.actors)
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
