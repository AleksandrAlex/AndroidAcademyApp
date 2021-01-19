package ru.suslovalex.androidacademyapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Actor(
    val id: Int,
    val name: String,
    val actorImage: String,
): Parcelable