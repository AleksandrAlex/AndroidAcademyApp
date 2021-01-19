package ru.suslovalex.androidacademyapp.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Genre(
    val id: Int,
    val name: String
): Parcelable