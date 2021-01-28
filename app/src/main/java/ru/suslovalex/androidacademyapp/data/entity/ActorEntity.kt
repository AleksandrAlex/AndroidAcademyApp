package ru.suslovalex.androidacademyapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.suslovalex.androidacademyapp.db.MovieDBContract

@Entity(tableName = MovieDBContract.ACTORS_TABLE_NAME)
data class ActorEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val actorImage: String,
    val movieId: Int
)