package dev.tutushkin.allmovies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.tutushkin.allmovies.R
import dev.tutushkin.allmovies.presentation.movies.view.MoviesFragment
import kotlinx.serialization.ExperimentalSerializationApi

// TODO Add loader
// TODO Add language selection
// TODO Add save favorites
// TODO Add movie search
// TODO Add info about actors (new screen)
// TODO Use Navigation
// TODO Use DI
// TODO Add column alignment to the RecyclerView
// TODO Optimize image sizes dynamically based on a display/network speed/settings
// TODO Add tests
// TODO Add logging
// TODO Replace Toasts with SnackBars

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        NewNode.init()
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesFragment())
                .commit()
        }
    }

}