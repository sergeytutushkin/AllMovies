package dev.tutushkin.lesson8.data.movies.local

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
}
