package ru.suslovalex.androidacademyapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.MovieEntity

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(movies: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(movies: List<GenreEntity>)

    @Query("SELECT * FROM table_movies ORDER BY voteAverage DESC")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM genreentity ")
    suspend fun getAllGenres(): List<GenreEntity>
}