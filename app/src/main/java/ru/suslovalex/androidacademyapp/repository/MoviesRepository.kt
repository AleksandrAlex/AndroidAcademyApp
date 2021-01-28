package ru.suslovalex.androidacademyapp.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.suslovalex.androidacademyapp.data.*
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.db.MoviesDao
import ru.suslovalex.androidacademyapp.db.MoviesDatabase
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.BASE_IMAGE_URL
import ru.suslovalex.androidacademyapp.retrofit.RemoteDataStore


class MoviesRepository(val remoteDataStore: RemoteDataStore) {


    suspend fun loadGenres(): List<Genre> = withContext(Dispatchers.IO) {

        return@withContext remoteDataStore.getGenres().genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    private suspend fun loadActors(movieID: Long): List<Actor> = withContext(Dispatchers.IO) {
        val actors = remoteDataStore.getActors(movieID).cast.map { actor ->
            Actor(id = actor.id, name = actor.name, actorImage = BASE_IMAGE_URL + actor.profilePath)
        }
        Log.d("Actors ", actors.toString())
        return@withContext actors
    }

    private suspend fun loadRuntime(movieID: Long): Int {
        return remoteDataStore.getRuntime(movieID).runtime
    }

    suspend fun loadUpcomingMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val genres = loadGenres()
        val genresMap = genres.associateBy { it.id }

        val movies = remoteDataStore.getUpcomingMovies().results.map { movie ->
            Movie(
                id = movie.id,
                adult = if (movie.adult) "16+" else "13+",
                backdropPath = movie.backdropPath,
                runtime = loadRuntime(movieID = movie.id.toLong()),
                overview = movie.overview, posterPath = movie.posterPath,
                releaseDate = movie.releaseDate, title = movie.title,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                actors = loadActors(movieID = movie.id.toLong()),
                genres = movie.genreIds.map {
                    genresMap[it] ?: Genre(0, "Empty genre...")
                }
            )
        }
        return@withContext movies
    }
}