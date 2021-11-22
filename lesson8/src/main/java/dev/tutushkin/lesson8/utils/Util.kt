package dev.tutushkin.lesson8.utils

import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun dateToYear(value: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(value)
            return SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
        }
    }
}