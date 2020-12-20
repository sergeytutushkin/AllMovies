package dev.tutushkin.lesson5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.tutushkin.lesson5.data.Movie

class MovieAdapter(
    private val movies: List<Movie>,
    private val clickListener: MoviesListClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_movie, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], clickListener)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val image: ImageView = view.findViewById(R.id.view_holder_movie_poster_image)
        private val age: TextView = view.findViewById(R.id.view_holder_movie_age_text)
        private val like: ImageView = view.findViewById(R.id.view_holder_movie_like_image)
        private val rating: RatingBar = view.findViewById(R.id.view_holder_movie_rating)
        private val genres: TextView = view.findViewById(R.id.view_holder_movie_genres_text)
        private val reviews: TextView = view.findViewById(R.id.view_holder_movie_reviews_text)
        private val title: TextView = view.findViewById(R.id.view_holder_movie_title_text)
        private val duration: TextView = view.findViewById(R.id.view_holder_movie_length_text)

        fun bind(movie: Movie, clickListener: MoviesListClickListener) {
            title.text = movie.title
            genres.text = movie.genres
            duration.text = view.context.getString(R.string.movies_list_duration, movie.duration)
            reviews.text = view.context.getString(R.string.movies_list_reviews, movie.reviews)
            image.setBackgroundResource(movie.posterSmall)
            age.text = movie.age
            if (movie.like) {
                like.setImageResource(R.drawable.ic_like)
            } else {
                like.setImageResource(R.drawable.ic_notlike)
            }
            rating.rating = movie.rating
            view.setOnClickListener { clickListener.onItemClick(movie) }
        }
    }
}

interface MoviesListClickListener {
    fun onItemClick(movie: Movie)
}
