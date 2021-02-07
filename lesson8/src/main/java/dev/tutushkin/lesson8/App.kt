package dev.tutushkin.lesson8

import android.app.Application
import dev.tutushkin.lesson8.data.AppDatabase

class App : Application() {

    val database by lazy { AppDatabase.getInstance(this) }
    //    lateinit var instance: App
//    lateinit var database: AppDatabase
//
//    override fun onCreate() {
//        super.onCreate()
//
////        instance = this
//        database = Room.databaseBuilder(
//            this,
//            AppDatabase::class.java,
//            "Movies.db"
//        )
//            .allowMainThreadQueries()   // TODO Delete!!!
//            .fallbackToDestructiveMigration()   // TODO Delete later!
//            .build()
//    }
}