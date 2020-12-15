package dev.tutushkin.lesson4

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesList() : Fragment(R.layout.fragment_movies_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.movie_list_recycler)
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val listener = object : MoviesListClickListener {
            override fun onItemClick(movie: Movie) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, FragmentMoviesDetails())
                    .commit()
            }
        }
        val movies = DataUtils().generateMovies()
        val adapter = MovieAdapter(movies, listener)

        recycler.adapter = adapter
    }

}