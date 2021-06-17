package dev.tutushkin.lesson8

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.tutushkin.lesson8.adapters.ActorsAdapter
import dev.tutushkin.lesson8.network.NetworkModule.genres
import dev.tutushkin.lesson8.viewmodels.MovieDetailsViewModel
import dev.tutushkin.lesson8.viewmodels.MovieDetailsViewModelFactory
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MovieDetailsFragment : Fragment(R.layout.fragment_movies_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg: Int = arguments?.getInt(MOVIES_KEY) ?: 0
        val viewModelFactory = MovieDetailsViewModelFactory(requireActivity().application, arg)

        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        val poster: ImageView = view.findViewById(R.id.movies_details_poster_image)
        val age: TextView = view.findViewById(R.id.movies_details_age_text)
        val title: TextView = view.findViewById(R.id.movies_details_title_text)
        val genre: TextView = view.findViewById(R.id.movies_details_genres_text)
        val rating: RatingBar = view.findViewById(R.id.movies_details_rating)
        val reviews: TextView = view.findViewById(R.id.movies_details_reviews_text)
        val storyline: TextView = view.findViewById(R.id.movies_details_storyline_content_text)
        val duration: TextView = view.findViewById(R.id.movies_details_duration_text)
        val recycler = view.findViewById<RecyclerView>(R.id.movie_details_actors_recycler)

        viewModel.currentMovie.observe(viewLifecycleOwner, {
            age.text = view.context.getString(R.string.movies_list_age, it.movie.minimumAge)
            title.text = it.movie.title
            genre.text = genres.filter { genreEntity ->
                it.movie.genres.contains(genreEntity.id)
            }.joinToString { genreEntity ->
                genreEntity.name
            }
            rating.rating = it.movie.ratings / 2
            reviews.text = view.context.getString(R.string.movie_details_reviews, it.movie.runtime)
            duration.text = view.context.getString(R.string.movies_list_duration, it.movie.runtime)
            storyline.text = it.movie.overview
            Glide.with(requireContext())
                .load(it.movie.backdrop)
                .into(poster)

            recycler.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            recycler.adapter = ActorsAdapter(it.actors)
        })

        view.findViewById<TextView>(R.id.movies_details_back_text).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
