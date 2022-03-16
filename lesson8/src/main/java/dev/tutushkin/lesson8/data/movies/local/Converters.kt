package dev.tutushkin.lesson8.data.movies.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toListOfIds(flatIdList: String?): List<Int> {
        if (flatIdList.isNullOrEmpty()) {
            return emptyList()
        }

//        return flatIdList.split(",").map { it.toInt() }
        return emptyList()
    }

    @TypeConverter
    fun fromListOfIds(listOfIds: List<Int>): String {
        return listOfIds.joinToString(",")
    }

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
