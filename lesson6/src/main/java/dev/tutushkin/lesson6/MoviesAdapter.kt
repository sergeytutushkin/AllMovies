package dev.tutushkin.lesson6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.tutushkin.lesson6.data.Movie

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
        private val rating: RatingBar = view.findViewById(R.id.view_holder_movie_rating)
        private val genres: TextView = view.findViewById(R.id.view_holder_movie_genres_text)
        private val reviews: TextView = view.findViewById(R.id.view_holder_movie_reviews_text)
        private val title: TextView = view.findViewById(R.id.view_holder_movie_title_text)
        private val duration: TextView = view.findViewById(R.id.view_holder_movie_length_text)

        fun bind(movie: Movie, clickListener: MoviesListClickListener) {
            title.text = movie.title
            genres.text = movie.genres.joinToString() { it.name }
            duration.text = view.context.getString(R.string.movies_list_duration, movie.runtime)
            reviews.text = view.context.getString(R.string.movies_list_reviews, movie.numberOfRatings)
            age.text = view.context.getString(R.string.movies_list_age, movie.minimumAge)
            rating.rating = movie.ratings / 2
            Glide.with(view.context)
                .load(movie.poster)
                .into(image)

            view.setOnClickListener { clickListener.onItemClick(movie) }
        }
    }
}

interface MoviesListClickListener {
    fun onItemClick(movie: Movie)
}
