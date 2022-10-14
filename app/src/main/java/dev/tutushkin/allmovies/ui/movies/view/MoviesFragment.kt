package dev.tutushkin.allmovies.ui.movies.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.tutushkin.allmovies.R
import dev.tutushkin.allmovies.databinding.FragmentMoviesListBinding
import dev.tutushkin.allmovies.ui.movies.viewmodel.MoviesState
import dev.tutushkin.allmovies.ui.movies.viewmodel.MoviesViewModel

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesViewModel by viewModels()

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// TODO Column alignment RecyclerView
//        val displayMetrics = DisplayMetrics()
//            ...

        _binding = FragmentMoviesListBinding.bind(view)

        val spanCount = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
        binding.moviesListRecycler.layoutManager = GridLayoutManager(requireContext(), spanCount)

        val listener = object : MoviesClickListener {
            override fun onItemClick(movieId: Int) {
                val action =
                    MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(movieId)
                view.findNavController().navigate(action)
            }
        }

        adapter = MoviesAdapter(listener)
        binding.moviesListRecycler.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, ::handleMoviesList)
    }

    private fun handleMoviesList(state: MoviesState) {
        when (state) {
            is MoviesState.Result -> {
//                hideLoading()
//                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                adapter.submitList(state.result)
            }
            is MoviesState.Error -> {
//                hideLoading()
                Toast.makeText(requireContext(), state.e.message, Toast.LENGTH_SHORT).show()
            }
            is MoviesState.Loading -> //showLoading()
            {
//                showLoading()
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
