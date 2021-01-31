package dev.tutushkin.lesson8.data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface ActorDao {
    @Query("SELECT * FROM actors")
    fun getAll(): List<Actor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(actors: List<Actor>)

    @Query("DELETE FROM actors")
    fun deleteAll()
}