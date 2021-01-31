package dev.tutushkin.lesson8.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "actors")
data class Actor(
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "photo")
    val photo: String
)
