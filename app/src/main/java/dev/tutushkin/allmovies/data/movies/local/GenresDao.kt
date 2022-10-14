package dev.tutushkin.allmovies.data.movies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GenresDao {

    @Query("SELECT * FROM genres")
    suspend fun getAll(): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<GenreEntity>)

    @Query("DELETE FROM genres")
    suspend fun deleteAll()
}
