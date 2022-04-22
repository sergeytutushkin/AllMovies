package dev.tutushkin.lesson8.data.movies.local

import androidx.room.*

@Dao
interface MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies")
    fun getNowPlaying(): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :id")
    fun getMovieDetails(id: Int): MovieEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieEntity>)

//    @Update
//    suspend fun update(movie: MovieEntity)
//
//    @Query("DELETE FROM movies WHERE id = :id")
//    suspend fun delete(id: Int)

    @Query("DELETE FROM movies")
    fun deleteAll()
}