package ru.suslovalex.androidacademyapp.model

data class Movie(
    val id: Int,
    val image: Int,
    val adult: Int,
    val isLike: Boolean,
    val genre: String,
    val rating: Float,
    val reviewers: Long,
    val title: String,
    val timeLimit: Long
)