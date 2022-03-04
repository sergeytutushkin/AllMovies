package dev.tutushkin.lesson8.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.tutushkin.lesson8.R
import dev.tutushkin.lesson8.presentation.movies.view.MoviesFragment
import kotlinx.serialization.ExperimentalSerializationApi

// TODO Add loader
// TODO Add movie poster placeholder
// TODO Add actors photo placeholder
// TODO Add language selection
// TODO Add save favorites
// TODO Add movie search
// TODO Add info about actors (new screen)
// TODO Use Navigation
// TODO Use ViewBinding
// TODO Use DI
// TODO Add repositories and transfer data processing logic to them
// TODO Add column alignment to the RecyclerView
// TODO Add tests

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesFragment())
                .commit()
        }
    }

}