package dev.tutushkin.lesson8.data

// TODO Implement Result class and use
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}