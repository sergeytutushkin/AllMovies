package dev.tutushkin.allmovies.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDetailsDao {

    @Query("SELECT * FROM movie_details WHERE id = :id")
    suspend fun getMovieDetails(id: Int): MovieDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieDetailsEntity): Long

    @Query("UPDATE movie_details SET isActorsLoaded = 1 WHERE id = :id")
    suspend fun setActorsLoaded(id: Int)

    @Query("DELETE FROM movie_details WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM movie_details")
    suspend fun clear()
}
