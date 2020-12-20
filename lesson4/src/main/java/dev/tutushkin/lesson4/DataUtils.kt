package dev.tutushkin.lesson4

class DataUtils {
    fun generateMovies(): List<Movie> {
        return listOf(
            Movie(
                "Avengers: End Game",
                R.drawable.movie_avengers_end_games_small,
                R.drawable.movie_avengers_end_games_big,
                "Action, Adventure, Drama",
                3f,
                125,
                137,
                "13+",
                false,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                generateActors()
            ),
            Movie(
                "Tenet",
                R.drawable.movie_tenet,
                null,
                "Action, Sci-Fi, Thriller",
                5f,
                98,
                97,
                "16+",
                true,
                "",
                null
            ),
            Movie(
                "Black Widow",
                R.drawable.movie_black_widow,
                null,
                "Action, Adventure, Sci-Fi",
                4f,
                38,
                102,
                "13+",
                false,
                "",
                null
            ),
            Movie(
                "Wonder Woman 1984",
                R.drawable.movie_wonder_woman_1984,
                null,
                "Action, Adventure, Fantasy",
                5f,
                74,
                120,
                "13+",
                false,
                "",
                null
            )
        )
    }

    fun generateActors(): List<Actor> {
        return listOf(
            Actor(
                "Robert Downey Jr.",
                R.drawable.robert_downey_jr
            ),
            Actor(
                "Chris Evans",
                R.drawable.chris_evans
            ),
            Actor(
                "Mark Ruffalo",
                R.drawable.mark_ruffalo
            ),
            Actor(
                "Chris Hemsworth",
                R.drawable.chris_hemsworth
            )
        )
    }

}