package dev.tutushkin.lesson8.presentation.movies.view

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.data.core.network.NetworkModule
import dev.tutushkin.lesson8.data.movies.local.MovieEntity

class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val image: ImageView = view.findViewById(R.id.view_holder_movie_poster_image)
    private val age: TextView = view.findViewById(R.id.view_holder_movie_age_text)
    private val rating: RatingBar = view.findViewById(R.id.view_holder_movie_rating)
    private val genre: TextView = view.findViewById(R.id.view_holder_movie_genres_text)
    private val reviews: TextView = view.findViewById(R.id.view_holder_movie_reviews_text)
    private val title: TextView = view.findViewById(R.id.view_holder_movie_title_text)
    private val year: TextView = view.findViewById(R.id.view_holder_movie_year_text)

    fun bind(item: MovieEntity, clickListener: MoviesClickListener) {
        title.text = item.title
        genre.text = NetworkModule.genres.filter {
            item.genres.contains(it.id)
        }.joinToString { it.name }
        year.text = item.year
        reviews.text =
            view.context.getString(R.string.movies_list_reviews, item.numberOfRatings)
        age.text = view.context.getString(R.string.movies_list_age, item.minimumAge)
        rating.rating = item.ratings / 2
        Glide.with(view.context)
            .load(item.poster)
            .into(image)

        view.setOnClickListener { clickListener.onItemClick(item.id) }
    }
}