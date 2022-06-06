package dev.tutushkin.allmovies.data.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.tutushkin.allmovies.data.movies.local.*

@Database(
    entities = [
        MovieListEntity::class,
        MovieDetailsEntity::class,
        GenreEntity::class,
        ActorEntity::class,
        ConfigurationEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDb : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
    abstract fun movieDetails(): MovieDetailsDao
    abstract fun genresDao(): GenresDao
    abstract fun actorsDao(): ActorsDao
    abstract fun configurationDao(): ConfigurationDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDb? = null

        fun getDatabase(context: Context): MoviesDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDb::class.java,
                    "Movies.db"
                )
//                    .allowMainThreadQueries()   // TODO Delete!!!
                    .fallbackToDestructiveMigration()   // TODO Delete later!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

}