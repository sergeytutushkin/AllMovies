package dev.tutushkin.lesson8.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "poster")
    val poster: String,

    @ColumnInfo(name = "backdrop")
    val backdrop: String,

    @ColumnInfo(name = "ratings")
    val ratings: Float,

    @ColumnInfo(name = "numberOfRatings")
    val numberOfRatings: Int,

    @ColumnInfo(name = "minimumAge")
    val minimumAge: Int,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "runtime")
    val runtime: Int = 0,

    @ColumnInfo(name = "genres_id")
    val genres: List<Int> = listOf(),

    @ColumnInfo(name = "actors_id")
    val actors: List<Int> = listOf()
)