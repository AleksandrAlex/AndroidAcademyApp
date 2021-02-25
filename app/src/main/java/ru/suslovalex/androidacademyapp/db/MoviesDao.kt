package ru.suslovalex.androidacademyapp.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.MovieEntity
import ru.suslovalex.androidacademyapp.data.entity.relation.MovieWithActorsAndGenres

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(movies: List<ActorEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(movies: List<GenreEntity>)

    @Transaction
    @Query("SELECT * FROM table_movies ORDER by releaseDate ASC")
    fun getMovies(): Flow<List<MovieWithActorsAndGenres>>

    @Transaction
    @Query("SELECT * FROM table_movies WHERE id = :id")
    suspend fun getMovie(id: Long): MovieWithActorsAndGenres

    @Query("DELETE FROM table_movies")
    suspend fun deleteTableMovies()

    @Query("DELETE FROM table_genres")
    suspend fun deleteTableGenres()

    @Query("DELETE FROM table_actors")
    suspend fun deleteTableActors()


}