package ru.suslovalex.androidacademyapp.data




import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Int,
    val results: List<Result>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)