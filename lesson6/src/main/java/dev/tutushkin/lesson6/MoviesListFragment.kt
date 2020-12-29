package dev.tutushkin.lesson6

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson6.adapters.MoviesAdapter
import dev.tutushkin.lesson6.adapters.MoviesListClickListener
import dev.tutushkin.lesson6.data.Movie
import dev.tutushkin.lesson6.viewmodels.MoviesListViewModel
import dev.tutushkin.lesson6.viewmodels.MoviesListViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

const val MOVIES_KEY = "MOVIES"

class MoviesListFragment() : Fragment(R.layout.fragment_movies_list) {

    private val scope = CoroutineScope(Dispatchers.IO)
    var movies: List<Movie> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = MoviesListViewModelFactory(application)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(MoviesListViewModel::class.java)

        val recycler = view.findViewById<RecyclerView>(R.id.movies_list_recycler)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val listener = object : MoviesListClickListener {
            override fun onItemClick(movie: Movie) {
                val bundle = Bundle()
                bundle.putParcelable(MOVIES_KEY, movie)
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
    }

}