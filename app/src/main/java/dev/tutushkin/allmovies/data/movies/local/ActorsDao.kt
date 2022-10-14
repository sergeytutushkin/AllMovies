package dev.tutushkin.allmovies.data.movies.local

import androidx.room.*

@Dao
interface ActorsDao {

    @Transaction
    @Query("SELECT * FROM actors WHERE id IN (:actorsId)")
    suspend fun getActors(actorsId: List<Int>): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(actors: List<ActorEntity>)

    @Query("DELETE FROM actors")
    suspend fun deleteAll()
}
