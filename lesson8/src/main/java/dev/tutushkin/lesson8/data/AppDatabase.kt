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
//        private const val DATABASE_NAME = "Movies.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Movies.db"
                )
                    .allowMainThreadQueries()   // TODO Delete!!!
                    .fallbackToDestructiveMigration()   // TODO Delete later!
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
//        @Volatile
//        private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//
//        private fun buildDatabase(context: Context): AppDatabase {
//            return Room.databaseBuilder(context., AppDatabase::class.java, DATABASE_NAME)
//                .allowMainThreadQueries()   // TODO Delete!!!
//                .fallbackToDestructiveMigration()   // TODO Delete later!
//                .build()
//        }
//    }
}