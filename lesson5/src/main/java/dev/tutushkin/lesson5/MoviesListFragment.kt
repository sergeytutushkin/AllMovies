package dev.tutushkin.lesson5

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson5.data.Movie
import dev.tutushkin.lesson5.data.loadMovies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val MOVIES_KEY = "MOVIES"

class MoviesListFragment() : Fragment(R.layout.fragment_movies_list) {

    private val scope = CoroutineScope(Dispatchers.IO)
    var movies: List<Movie> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        scope.launch {
            movies = loadMovies(requireContext())
            withContext(Dispatchers.Main) {
                recycler.adapter = movies?.let { MovieAdapter(it, listener) }
            }
        }
    }

}