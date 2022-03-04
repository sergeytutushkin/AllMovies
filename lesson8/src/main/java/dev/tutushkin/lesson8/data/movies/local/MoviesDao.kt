package dev.tutushkin.lesson8.data.movies.local

import androidx.room.*

@Dao
interface MoviesDao {
    @Transaction
    @Query("SELECT * FROM movies")
    suspend fun getNowPlaying(): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieDetails(id: Int): MovieEntity

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity): Long

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

//    @Update
//    suspend fun update(movie: MovieEntity)
//
//    @Query("DELETE FROM movies WHERE id = :id")
//    suspend fun delete(id: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}