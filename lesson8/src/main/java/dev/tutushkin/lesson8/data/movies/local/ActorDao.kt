package dev.tutushkin.lesson8.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ActorDao {
    @Query("SELECT * FROM actors")
    fun getAll(): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(actors: List<ActorEntity>)

    @Query("DELETE FROM actors")
    fun deleteAll()
}