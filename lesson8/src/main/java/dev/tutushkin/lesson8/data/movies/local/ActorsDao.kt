package dev.tutushkin.lesson8.data.movies.local

import androidx.room.*

@Dao
interface ActorsDao {

    @Transaction
    @Query("SELECT * FROM actors WHERE id = :movieId")
    fun getActors(movieId: Int): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(actors: List<ActorEntity>)

    @Query("DELETE FROM actors")
    fun deleteAll()
}