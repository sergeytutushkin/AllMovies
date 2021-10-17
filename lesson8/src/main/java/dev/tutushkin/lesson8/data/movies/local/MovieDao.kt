package dev.tutushkin.lesson8.data.movies.local

import androidx.room.*
import dev.tutushkin.lesson8.domain.MovieWithActors

@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieEntity>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieWithActors(id: Int): MovieWithActors

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity): Long

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()
}