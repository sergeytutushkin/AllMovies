package dev.tutushkin.lesson3

import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class FragmentMoviesList() : Fragment(R.layout.fragment_movies_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.movies_list_movie_card).apply {
            setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.main_container, FragmentMoviesDetails())
                    .commit()
            }
        }
    }

}