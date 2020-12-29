package ru.suslovalex.androidacademyapp.domain

import kotlinx.coroutines.delay
import ru.suslovalex.androidacademyapp.data.Movie

class MovieChecker{

    suspend fun loadMoviesList(movies: List<Movie>): MovieResponseResult {
        delay(3000)
        return if (movies.isEmpty()){
            MovieResponseResult.Error()
        } else{
            MovieResponseResult.Success()
        }
    }

    suspend fun loadMovie(movie: Movie): MovieResponseResult {
        delay(3000)
        return if (movie.equals(null)){
            MovieResponseResult.Error()
        } else{
            MovieResponseResult.Success()
        }
    }
}