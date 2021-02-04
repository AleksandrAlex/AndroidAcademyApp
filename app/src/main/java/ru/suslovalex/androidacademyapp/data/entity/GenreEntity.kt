package ru.suslovalex.androidacademyapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.suslovalex.androidacademyapp.db.MovieDBContract

@Entity(tableName = MovieDBContract.GENRES_TABLE_NAME)
data class GenreEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val movieId: Long
)
