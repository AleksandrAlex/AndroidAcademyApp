package ru.suslovalex.androidacademyapp.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.MovieEntity

data class MovieWithGenres (
    @Embedded
    val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val actors: List<GenreEntity>
        )