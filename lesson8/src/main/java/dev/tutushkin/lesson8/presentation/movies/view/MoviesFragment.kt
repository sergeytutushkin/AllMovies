package dev.tutushkin.lesson8.presentation.movies.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.presentation.moviedetails.view.MovieDetailsFragment
import dev.tutushkin.lesson8.presentation.movies.viewmodel.MoviesViewModel
import dev.tutushkin.lesson8.presentation.movies.viewmodel.MoviesViewModelFactory
import kotlinx.serialization.ExperimentalSerializationApi

const val MOVIES_KEY = "MOVIES"

@ExperimentalSerializationApi
class MoviesFragment : Fragment(R.layout.fragment_movies_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// TODO Column alignment RecyclerView
//        val displayMetrics = DisplayMetrics()
//            ...

        val viewModelFactory = MoviesViewModelFactory(requireActivity().application)

        val viewModel = ViewModelProvider(this, viewModelFactory)[MoviesViewModel::class.java]

        val recycler = view.findViewById<RecyclerView>(R.id.movies_list_recycler)
        val spanCount = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
        recycler.layoutManager = GridLayoutManager(requireContext(), spanCount)

        val listener = object : MoviesClickListener {
            override fun onItemClick(movieId: Long) {
                val bundle = Bundle()
                bundle.putLong(MOVIES_KEY, movieId)
                val detailsFragment = MovieDetailsFragment()
                detailsFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, detailsFragment)
                    .commit()
            }
        }

        val adapter = MoviesAdapter(listener)
        recycler.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

//    private fun handleMoviesList(state: MoviesResult) {
//        when (state) {
//            is MoviesResult.SuccessResult -> {
//                hideLoading()
//                binding.moviesPlaceholder.toGone()
//                binding.moviesList.toVisible()
//                moviesAdapter.submitList(state.result)
//            }
//            is ErrorResult -> {
//                hideLoading()
//                hideAndSetEmptyList()
//                binding.moviesPlaceholder.setText(R.string.search_error)
//                Timber.e("Something went wrong.", state.e)
//            }
//            is EmptyResult -> {
//                hideLoading()
//                hideAndSetEmptyList()
//                binding.moviesPlaceholder.setText(R.string.empty_result)
//            }
//            is EmptyQuery -> {
//                hideLoading()
//                hideAndSetEmptyList()
//                binding.moviesPlaceholder.setText(R.string.movies_placeholder)
//            }
//            is Loading -> showLoading()
//        }
//    }

}