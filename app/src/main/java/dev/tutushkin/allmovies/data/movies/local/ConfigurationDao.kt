package dev.tutushkin.allmovies.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ConfigurationDao {

    @Query("SELECT * FROM configuration")
    suspend fun get(): ConfigurationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(configuration: ConfigurationEntity): Long

    @Query("DELETE FROM configuration")
    suspend fun delete()
}
