package dev.tutushkin.allmovies.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import dev.tutushkin.allmovies.R
import kotlinx.serialization.ExperimentalSerializationApi

// TODO Add loader
// TODO Add multi language support
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
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .add(R.id.main_container, MoviesFragment())
//                .commit()
//        }
    }

}