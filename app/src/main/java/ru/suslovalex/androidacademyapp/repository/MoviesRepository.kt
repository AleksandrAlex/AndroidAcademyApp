package ru.suslovalex.androidacademyapp.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.suslovalex.androidacademyapp.data.*
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.MovieEntity
import ru.suslovalex.androidacademyapp.data.entity.relation.MovieWithActorsAndGenres
import ru.suslovalex.androidacademyapp.db.MoviesDatabase
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.BASE_IMAGE_URL
import ru.suslovalex.androidacademyapp.retrofit.RemoteDataStore


class MoviesRepository(val remoteDataStore: RemoteDataStore, val moviesDatabase: MoviesDatabase) {


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
            Actor(id = actor.id, name = actor.name, actorImage = if(actor.profilePath != null) BASE_IMAGE_URL + actor.profilePath else null)
        }
        Log.d("Actors ", actors.toString())
        return@withContext actors
    }

    private suspend fun loadRuntime(movieID: Long): Int {
        return remoteDataStore.getRuntime(movieID).runtime
    }

    @Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
    suspend fun loadUpcomingMovies(): List<Movie> = withContext(Dispatchers.IO) {
        val genres = loadGenres()
        Log.d("Genres: ","$genres")
        val genresMap = genres.associateBy { it.id }

        val movies = remoteDataStore.getUpcomingMovies().results.map { movie ->
            Movie(
                id = movie.id,
                adult = if (movie.adult) "16+" else "13+",
                backdropPath = movie.backdropPath,
                runtime = loadRuntime(movieID = movie.id),
                overview = movie.overview,
                posterPath = movie.posterPath,
                releaseDate = movie.releaseDate,
                title = movie.title,
                voteAverage = movie.voteAverage,
                voteCount = movie.voteCount,
                actors = loadActors(movieID = movie.id),
                genres = movie.genreIds.map {
                    genresMap[it] ?: Genre(0, "Empty genre...")
                }
            )
        }
        return@withContext movies
    }

    private suspend fun saveGenresToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {
        val genresEntity = movies.flatMap { movie ->
            movie.genres.map { genre ->
                GenreEntity(
                    id = genre.id,
                    name = genre.name,
                    movieId = movie.id
                )
            }
        }
        moviesDatabase.moviesDao().insertGenres(genresEntity)
    }

        private suspend fun saveActorsToDataBase(movies: List<Movie>) = withContext(Dispatchers.IO) {
            val actorEntity = movies.flatMap { movie ->
                movie.actors.map { actor ->
                    ActorEntity(
                        id = actor.id,
                        name = actor.name,
                        actorImage = actor.actorImage,
                        movieId = movie.id
                    )
                }
            }
            moviesDatabase.moviesDao().insertActors(actorEntity)
        }

        private suspend fun saveMoviesToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO) {
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
            moviesDatabase.moviesDao().insertMovies(movieEntity)
        }

    suspend fun saveDatesToDatabase(movies: List<Movie>) = withContext(Dispatchers.IO){
        saveMoviesToDatabase(movies)
        saveGenresToDatabase(movies)
        saveActorsToDataBase(movies)
    }

    suspend fun readMoviesFromDatabase(): List<Movie> {
        val listMoviesWithActorsAndGenres = getMoviesFromDatabase()
        Log.d("readFromDatabase: ", "$listMoviesWithActorsAndGenres")
        return convertDataToMovies(listMoviesWithActorsAndGenres)
    }

    suspend fun deleteAllDatabase() = withContext(Dispatchers.IO){
        moviesDatabase.moviesDao().deleteTableMovies()
        moviesDatabase.moviesDao().deleteTableGenres()
        moviesDatabase.moviesDao().deleteTableActors()
    }

    private fun convertDataToMovies(listMoviesWithActorsAndGenres: List<MovieWithActorsAndGenres>): List<Movie> {
        return listMoviesWithActorsAndGenres.map { listData ->
            Movie(
                id = listData.movieEntity.id,
                adult = if (listData.movieEntity.adult) "16+" else "13+",
                backdropPath = listData.movieEntity.backdropPath,
                runtime = listData.movieEntity.runtime,
                overview = listData.movieEntity.overview,
                posterPath = listData.movieEntity.posterPath,
                releaseDate = listData.movieEntity.releaseDate,
                title = listData.movieEntity.title,
                voteAverage = listData.movieEntity.voteAverage,
                voteCount = listData.movieEntity.voteCount,
                actors = convertDataToActors(listData.actors),
                genres = convertDataToGenres(listData.genres)
            )
        }
    }

    private fun convertDataToGenres(genres: List<GenreEntity>): List<Genre> {
        return genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    private fun convertDataToActors(actors: List<ActorEntity>): List<Actor> {
        return actors.map {
            Actor(
                id = it.id,
                name = it.name,
                actorImage = it.actorImage
            )
        }
    }

    private suspend fun getMoviesFromDatabase()  = withContext(Dispatchers.IO){
        moviesDatabase.moviesDao().getMovies()
    }
}


