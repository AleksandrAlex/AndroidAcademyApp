package ru.suslovalex.androidacademyapp.data



import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

data class MoviesResponse(
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)