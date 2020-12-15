package dev.tutushkin.lesson4

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.movies_details_back_text).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}