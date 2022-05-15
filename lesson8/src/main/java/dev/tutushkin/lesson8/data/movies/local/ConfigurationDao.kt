package dev.tutushkin.lesson8.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConfigurationDao {

    @Query("SELECT * FROM configuration")
    fun get(): ConfigurationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(configuration: ConfigurationEntity): Long

    @Query("DELETE FROM configuration")
    fun delete()
}