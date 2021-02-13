package ru.suslovalex.androidacademyapp.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ru.suslovalex.androidacademyapp.data.*
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.MovieEntity
import ru.suslovalex.androidacademyapp.data.entity.relation.MovieWithActorsAndGenres
import ru.suslovalex.androidacademyapp.db.MoviesDatabase
import ru.suslovalex.androidacademyapp.retrofit.RemoteDataStore


class MoviesRepository(
    private val remoteDataStore: RemoteDataStore,
    private val moviesDataBase: MoviesDatabase
) {

    private val moviesDao = moviesDataBase.moviesDao

    @Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
    suspend fun loadUpcomingMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val genres = remoteDataStore.loadGenres()
        Log.d("Genres: ", "$genres")
        val genresMap = genres.associateBy { it.id }

        val movies = remoteDataStore.getUpcomingMovies().results.map { movie ->
            Movie(
                id = movie.id,
                adult = if (movie.adult) "16+" else "13+",
                backdropPath = movie.backdropPath,
                runtime = remoteDataStore.loadRuntime(movieID = movie.id),
                overview = movie.overview,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                actors = remoteDataStore.loadActors(movieID = movie.id),
                genres = movie.genreIds.map {
                    genresMap[it] ?: Genre(0, "Empty genre...")
                }
            )
        }
        return@withContext movies
    }

//    private suspend fun saveGenresToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {
//        val genresEntity = movies.flatMap { movie ->
//            movie.genres.map { genre ->
//                GenreEntity(
//                    id = genre.id,
//                    name = genre.name,
//                    movieId = movie.id
//                )
//            }
//        }
//        moviesDao.insertGenres(genresEntity)
//    }

//    private suspend fun saveActorsToDataBase(movies: List<Movie>) = withContext(Dispatchers.IO) {
//        val listActorEntity = movies.flatMap { movie ->
//            movie.actors.map { actor ->
//                ActorEntity(
//                    id = actor.id,
//                    name = actor.name,
//                    actorImage = actor.actorImage,
//                    castId = actor.cast_id,
//                    movieId = movie.id
//                )
//            }
//        }
//        moviesDao.insertActors(listActorEntity)
//    }

//    private suspend fun saveMoviesToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {
//        val movieEntity = movies.map { movie ->
//            MovieEntity(
//                id = movie.id,
//                adult = movie.adult == "16+",
//                backdropPath = movie.backdropPath,
//                overview = movie.overview,
//                posterPath = movie.posterPath,
//                releaseDate = movie.releaseDate,
//                title = movie.title,
//                voteAverage = movie.voteAverage,
//                voteCount = movie.voteCount,
//                runtime = movie.runtime
//            )
//        }
//        moviesDao.insertMovies(movieEntity)
//    }

    suspend fun saveDatesToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {
//        saveMoviesToDatabase(movies)
        val movieEntity = movies.map { movie ->
            MovieEntity(
                id = movie.id,
                adult = movie.adult == "16+",
                backdropPath = movie.backdropPath,
                overview = movie.overview,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                runtime = movie.runtime
            )
        }
        moviesDao.insertMovies(movieEntity)

//        saveGenresToDatabase(movies)
        val genresEntity = movies.flatMap { movie ->
            movie.genres.map { genre ->
                GenreEntity(
                    id = genre.id,
                    name = genre.name,
                    movieId = movie.id
                )
            }
        }
        moviesDao.insertGenres(genresEntity)

//        saveActorsToDataBase(movies)
        val listActorEntity = movies.flatMap { movie ->
            movie.actors.map { actor ->
                ActorEntity(
                    id = actor.id,
                    name = actor.name,
                    actorImage = actor.actorImage,
                    castId = actor.cast_id,
                    movieId = movie.id
                )
            }
        }
        moviesDao.insertActors(listActorEntity)
    }

    fun readMoviesFromDatabase(): Flow<List<MovieWithActorsAndGenres>> {
        return moviesDao.getMovies()
    }

    suspend fun deleteAllDatabase() = withContext(Dispatchers.IO) {
        moviesDao.deleteTableMovies()
        moviesDao.deleteTableGenres()
        moviesDao.deleteTableActors()
    }


}


