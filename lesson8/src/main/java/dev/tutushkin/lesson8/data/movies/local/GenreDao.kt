package dev.tutushkin.lesson8.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAll(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    fun deleteAll()
}