package dev.tutushkin.lesson4

class MoviesDataSource {
    fun generateMovies(): List<Movie> {
        return listOf(
            Movie(
                "Avengers: End Game",
                R.drawable.movie_avengers_end_games,
                "Action, Adventure, Drama",
                3f,
                125,
                137,
                "13+",
                false
            ),
            Movie(
                "Tenet",
                R.drawable.movie_tenet,
                "Action, Sci-Fi, Thriller",
                5f,
                98,
                97,
                "16+",
                true
            ),
            Movie(
                "Black Widow",
                R.drawable.movie_black_widow,
                "Action, Adventure, Sci-Fi",
                4f,
                38,
                102,
                "13+",
                false
            ),
            Movie(
                "Wonder Woman 1984",
                R.drawable.movie_wonder_woman_1984,
                "Action, Adventure, Fantasy",
                5f,
                74,
                120,
                "13+",
                false
            )
        )
    }
}