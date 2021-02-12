package ru.suslovalex.androidacademyapp.data


import kotlinx.serialization.Serializable

@Serializable
data class ActorResponse(
    val id: Long,
    val cast: List<Cast>
)