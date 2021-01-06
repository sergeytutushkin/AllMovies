package dev.tutushkin.lesson7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// TODO Add loader
// TODO Use Navigation
// TODO Add tests

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