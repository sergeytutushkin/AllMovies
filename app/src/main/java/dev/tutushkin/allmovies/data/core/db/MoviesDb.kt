package dev.tutushkin.allmovies.data.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.hilt.android.qualifiers.ApplicationContext
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
    abstract fun movieDetailsDao(): MovieDetailsDao
    abstract fun genresDao(): GenresDao
    abstract fun actorsDao(): ActorsDao
    abstract fun configurationDao(): ConfigurationDao

    companion object {

        fun create(@ApplicationContext appContext: Context): MoviesDb =
            Room.databaseBuilder(
                appContext,
                MoviesDb::class.java,
                "Movies.db"
            )
                .fallbackToDestructiveMigration()
                .build()

    }
}
