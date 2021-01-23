package ru.suslovalex.androidacademyapp.data


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GenreResponse(
    val genres: List<Genre>
)