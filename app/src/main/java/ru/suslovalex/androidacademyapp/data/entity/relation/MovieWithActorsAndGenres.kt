package ru.suslovalex.androidacademyapp.data.entity.relation

import androidx.room.Embedded
import androidx.room.Relation
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.MovieEntity

data class MovieWithActorsAndGenres(
    @Embedded
    val movieEntity: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = ActorEntity::class
    )
    val actors: List<ActorEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId",
        entity = GenreEntity::class
    )
    val genres: List<GenreEntity>
)