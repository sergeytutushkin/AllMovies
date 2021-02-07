package dev.tutushkin.lesson8

import android.app.Application
import dev.tutushkin.lesson8.data.AppDatabase

class App : Application() {

    val appContext = this
    val database by lazy { AppDatabase.instance }

//    override fun onCreate() {
//        super.onCreate()
//
//        database = AppDatabase.getInstance(this)
//    }
}