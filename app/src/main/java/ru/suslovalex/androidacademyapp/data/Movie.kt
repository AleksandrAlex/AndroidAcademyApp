package ru.suslovalex.androidacademyapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Long,
    val adult: String,
    val backdropPath: String,
    val runtime: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val actors: List<Actor>,
    val genres: List<Genre>
): Parcelable