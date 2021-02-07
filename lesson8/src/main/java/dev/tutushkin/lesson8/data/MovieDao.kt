package dev.tutushkin.lesson8.data

import androidx.room.*

@Dao
interface MovieDao {
    @Transaction
    @Query("SELECT * FROM movies")
    fun getAll(): List<MovieWithGenres>

    @Transaction
    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int): MovieWithGenresAndActors

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<MovieWithGenres>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieWithGenresAndActors): Long

    @Update
    fun update(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM movies")
    fun deleteAll()
}