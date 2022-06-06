package dev.tutushkin.allmovies.data.movies.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun toListOfStrings(flatStringList: String?): List<String> {
        if (flatStringList.isNullOrEmpty()) {
            return emptyList()
        }

        return flatStringList.split(",")
    }

    @TypeConverter
    fun fromListOfStrings(strings: List<String>): String {
        return strings.joinToString(",")
    }

    @TypeConverter
    fun toListOfInt(flatIntList: String?): List<Int> {
        if (flatIntList.isNullOrEmpty()) {
            return emptyList()
        }

        return flatIntList.split(",").map {
            it.toInt()
        }
    }

    @TypeConverter
    fun fromListOfInt(list: List<Int>): String {
        return list.joinToString(",")
    }
}
