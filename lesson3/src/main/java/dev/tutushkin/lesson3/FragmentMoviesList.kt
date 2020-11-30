package dev.tutushkin.lesson3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class FragmentMoviesList : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<CardView>(R.id.movies_list_movie_card).apply {
            setOnClickListener {
                activity?.let {
                    it.supportFragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_container, FragmentMoviesDetails())
                        .commit()
                }
            }
        }
    }

}