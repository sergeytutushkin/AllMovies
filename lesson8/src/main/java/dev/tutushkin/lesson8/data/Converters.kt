package dev.tutushkin.lesson8.data

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toListOfId(flatIdList: String?): List<Int> {
        if (flatIdList.isNullOrEmpty()) {
            return emptyList()
        }

        return flatIdList.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromListOfIds(listOfIds: List<Int>): String {
        return listOfIds.joinToString(",")
    }
}
