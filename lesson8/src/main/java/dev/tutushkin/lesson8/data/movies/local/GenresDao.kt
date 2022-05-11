package dev.tutushkin.lesson8.data.movies.local

import androidx.room.*

@Dao
interface GenresDao {

    @Transaction
    @Query("SELECT * FROM genres")
    fun getAll(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    fun deleteAll()
}