package dev.tutushkin.lesson8.presentation.movies.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.adapters.MoviesAdapter
import dev.tutushkin.lesson8.adapters.MoviesClickListener
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

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(MoviesViewModel::class.java)

        val recycler = view.findViewById<RecyclerView>(R.id.movies_list_recycler)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val listener = object : MoviesClickListener {
            override fun onItemClick(movieId: Int) {
                val bundle = Bundle()
                bundle.putInt(MOVIES_KEY, movieId)
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

        viewModel.movies.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

}