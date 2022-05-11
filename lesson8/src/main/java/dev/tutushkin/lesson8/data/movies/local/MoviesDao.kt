package dev.tutushkin.lesson8.data.movies.local

import androidx.room.*

@Dao
interface MoviesDao {

    @Transaction
    @Query("SELECT * FROM movies")
    fun getNowPlaying(): List<MovieListEntity>

    //    @Transaction
//    @Query("SELECT * FROM movies WHERE id = :id")
//    fun getMovieDetails(id: Int): MovieListEntity
//
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieListEntity>)

    //    @Update
//    suspend fun update(movie: MovieEntity)
//
    @Query("DELETE FROM movies WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM movies")
    fun clear()
}