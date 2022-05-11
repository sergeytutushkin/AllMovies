package dev.tutushkin.lesson8.presentation.movies.view

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.data.core.db.MoviesDb
import dev.tutushkin.lesson8.data.core.network.NetworkModule.moviesApi
import dev.tutushkin.lesson8.data.movies.MoviesRepositoryImpl
import dev.tutushkin.lesson8.data.movies.local.MoviesLocalDataSourceImpl
import dev.tutushkin.lesson8.data.movies.remote.MoviesRemoteDataSourceImpl
import dev.tutushkin.lesson8.databinding.FragmentMoviesListBinding
import dev.tutushkin.lesson8.presentation.moviedetails.view.MovieDetailsFragment
import dev.tutushkin.lesson8.presentation.movies.viewmodel.MoviesResult
import dev.tutushkin.lesson8.presentation.movies.viewmodel.MoviesViewModel
import dev.tutushkin.lesson8.presentation.movies.viewmodel.MoviesViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi

const val MOVIES_KEY = "MOVIES"

@ExperimentalSerializationApi
class MoviesFragment : Fragment(R.layout.fragment_movies_list) {

//    private val viewModel: MoviesViewModel by viewModels()

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

// TODO Column alignment RecyclerView
//        val displayMetrics = DisplayMetrics()
//            ...

        val db = MoviesDb.getDatabase(requireActivity().application)
        val remoteDataSource = MoviesRemoteDataSourceImpl(moviesApi)
        val localDataSource = MoviesLocalDataSourceImpl(
            db.moviesDao(),
            db.movieDetails(),
            db.configurationDao(),
            db.genresDao()
        )
        val repository =
            MoviesRepositoryImpl(remoteDataSource, localDataSource, Dispatchers.Default)
        val viewModel: MoviesViewModel by viewModels { MoviesViewModelFactory(repository) }

        _binding = FragmentMoviesListBinding.bind(view)

        val spanCount = when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
        binding.moviesListRecycler.layoutManager = GridLayoutManager(requireContext(), spanCount)

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

        adapter = MoviesAdapter(listener)
        binding.moviesListRecycler.adapter = adapter

        viewModel.movies.observe(viewLifecycleOwner, ::handleMoviesList)

//        viewModel.errorMessage.observe(viewLifecycleOwner) {
//            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
//        }
    }

    private fun handleMoviesList(state: MoviesResult) {
        when (state) {
            is MoviesResult.SuccessResult -> {
//                hideLoading()
                println("Fragment Success!!!")
//                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                adapter.submitList(state.result)
            }
            is MoviesResult.ErrorResult -> {
//                hideLoading()
                println("Fragment Error!!!")
                Toast.makeText(requireContext(), state.e.message, Toast.LENGTH_SHORT).show()
            }
            is MoviesResult.Loading -> //showLoading()
            {
//                showLoading()
                println("Fragment Loading!!!")
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