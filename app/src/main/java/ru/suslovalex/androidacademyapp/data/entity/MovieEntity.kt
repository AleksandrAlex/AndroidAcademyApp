package ru.suslovalex.androidacademyapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.suslovalex.androidacademyapp.db.MovieDBContract

@Entity(
    tableName = MovieDBContract.MOVIES_TABLE_NAME
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int
)