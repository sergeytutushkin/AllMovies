package dev.tutushkin.allmovies.data.movies.local

import androidx.room.*

@Dao
interface MoviesDao {

    @Transaction
    @Query("SELECT * FROM movies")
    suspend fun getNowPlaying(): List<MovieListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieListEntity>)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM movies")
    suspend fun clear()
}
