package dev.tutushkin.lesson7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.serialization.ExperimentalSerializationApi

// TODO Column alignment RecyclerView
// TODO Add loader
// TODO Move runtime from list to details
// TODO Add select language
// TODO Use Navigation
// TODO Use ViewBinding
// TODO Add tests

@ExperimentalSerializationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_container, MoviesListFragment())
                .commit()
        }
    }

}