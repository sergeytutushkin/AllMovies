package dev.tutushkin.allmovies.data.movies.local

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

    @Query("UPDATE movie_details SET isActorsLoaded = 1 WHERE id = :id")
    fun setActorsLoaded(id: Int)

    @Query("DELETE FROM movie_details WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM movie_details")
    fun clear()
}