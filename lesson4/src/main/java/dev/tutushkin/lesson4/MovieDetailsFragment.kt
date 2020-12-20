package dev.tutushkin.lesson4

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MovieDetailsFragment(private val movie: Movie) : Fragment(R.layout.fragment_movies_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val poster: ImageView = view.findViewById(R.id.movies_details_poster_image)
        val age: TextView = view.findViewById(R.id.movies_details_age_text)
        val title: TextView = view.findViewById(R.id.movies_details_title_text)
        val genres: TextView = view.findViewById(R.id.movies_details_genres_text)
        val rating: RatingBar = view.findViewById(R.id.movies_details_rating)
        val reviews: TextView = view.findViewById(R.id.movies_details_reviews_text)
        val storyline: TextView = view.findViewById(R.id.movies_details_storyline_content_text)
        val recycler = view.findViewById<RecyclerView>(R.id.movie_details_actors_recycler)

        age.text = movie.age
        title.text = movie.title
        genres.text = movie.genres
        rating.rating = movie.rating
        reviews.text = view.context.getString(R.string.movie_details_reviews, movie.duration)
        storyline.text = movie.storyline

        movie.posterBig?.let { poster.setBackgroundResource(it) }

        recycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        recycler.adapter = movie.actors?.let { ActorsAdapter(it) }

        view.findViewById<TextView>(R.id.movies_details_back_text).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}