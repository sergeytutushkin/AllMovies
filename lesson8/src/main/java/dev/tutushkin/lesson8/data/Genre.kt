package dev.tutushkin.lesson8.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "genres")
data class Genre(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
)
