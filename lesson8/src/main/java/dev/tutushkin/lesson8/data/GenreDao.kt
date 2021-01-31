package dev.tutushkin.lesson8.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<Genre>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)

    @Query("DELETE FROM genres")
    fun deleteAll()
}