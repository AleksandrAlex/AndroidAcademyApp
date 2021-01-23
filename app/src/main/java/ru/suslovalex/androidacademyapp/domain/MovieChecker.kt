package ru.suslovalex.androidacademyapp.domain

import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.Result

class MovieChecker{

    fun loadMoviesList(movies: List<Movie>): MovieResponseResult {
        return if (movies.isEmpty()){
            MovieResponseResult.Error
        } else{
            MovieResponseResult.Success
        }
    }

    fun loadMovie(movie: Movie?): MovieResponseResult {
        return if (movie==null){
            MovieResponseResult.Error
        } else{
            MovieResponseResult.Success
        }
    }
}