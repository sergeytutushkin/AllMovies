package dev.tutushkin.lesson8.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [MovieEntity::class, GenreEntity::class, ActorEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun actorDao(): ActorDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
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