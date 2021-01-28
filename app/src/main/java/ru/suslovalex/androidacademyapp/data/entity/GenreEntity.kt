package ru.suslovalex.androidacademyapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val movieID: Int
)
