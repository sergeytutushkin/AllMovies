package dev.tutushkin.lesson8.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM movie_details WHERE id = :id")
    fun getMovieDetails(id: Int): MovieDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDetailsEntity): Long

    @Query("DELETE FROM movie_details WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM movie_details")
    fun clear()
}