package dev.tutushkin.lesson8.presentation.movies.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.databinding.ViewHolderMovieBinding
import dev.tutushkin.lesson8.domain.movies.models.MovieList

class MovieViewHolder(
    private val binding: ViewHolderMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieList, clickListener: MoviesClickListener) {
        binding.apply {
            viewHolderMovieTitleText.text = item.title
            viewHolderMovieGenresText.text = item.genres
            viewHolderMovieYearText.text = item.year
            viewHolderMovieReviewsText.text =
                root.context.getString(R.string.movies_list_reviews, item.numberOfRatings)
            viewHolderMovieAgeText.text = item.minimumAge
            viewHolderMovieRating.rating = item.ratings / 2
            Glide.with(root.context)
                .load(item.poster)
                .into(viewHolderMoviePosterImage)

            root.setOnClickListener { clickListener.onItemClick(item.id) }
        }
    }
}