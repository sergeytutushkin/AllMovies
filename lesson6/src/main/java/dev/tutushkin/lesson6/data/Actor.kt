package dev.tutushkin.lesson6.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Actor(
    val id: Int,
    val name: String,
    val picture: String
) : Parcelable