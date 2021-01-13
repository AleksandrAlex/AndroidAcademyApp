package ru.suslovalex.androidacademyapp.domain

import ru.suslovalex.androidacademyapp.data.MoviesResponse
import ru.suslovalex.androidacademyapp.data.Result

class MovieChecker{

    fun loadMoviesList(moviesResponse: MoviesResponse): MovieResponseResult {
        return if (moviesResponse.results.isEmpty()){
            MovieResponseResult.Error
        } else{
            MovieResponseResult.Success
        }
    }

    fun loadMovie(movie: Result?): MovieResponseResult {
        return if (movie==null){
            MovieResponseResult.Error
        } else{
            MovieResponseResult.Success
        }
    }
}