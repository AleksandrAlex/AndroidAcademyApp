package ru.suslovalex.androidacademyapp.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.data.Actor
import ru.suslovalex.androidacademyapp.data.Genre
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.entity.relation.MovieWithActorsAndGenres
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult
import ru.suslovalex.androidacademyapp.repository.MoviesRepository

class MoviesDetailsViewModel(private val checker: MovieChecker, private val moviesRepository: MoviesRepository): ViewModel() {

    private val _state = MutableLiveData<MoviesDetailsState>()
    val state: LiveData<MoviesDetailsState>
    get() = _state

    fun getMovie(bundle: Bundle) = viewModelScope.launch (Dispatchers.IO){
        _state.postValue(MoviesDetailsState.Loading)
        val currentMovieId = bundle.getLong("movie")
        val currentMovie = moviesRepository.readMovieDetailFromDatabase(currentMovieId)
        val movie = convertDataToMovie(currentMovie)
        val newState = when(checker.loadMovie(movie)){
            MovieResponseResult.Success -> MoviesDetailsState.Success(movie)
            MovieResponseResult.Error -> MoviesDetailsState.Error("Error!")
        }
        _state.postValue(newState)
    }

    private fun convertDataToMovie(currentMovie: MovieWithActorsAndGenres): Movie {
        return Movie(
            id = currentMovie.movieEntity.id,
            adult = if (currentMovie.movieEntity.adult) "16+" else "13+",
            backdropPath = currentMovie.movieEntity.backdropPath,
            runtime = currentMovie.movieEntity.runtime,
            overview = currentMovie.movieEntity.overview,
            posterPath = currentMovie.movieEntity.posterPath,
            releaseDate = currentMovie.movieEntity.releaseDate,
            title = currentMovie.movieEntity.title,
            voteCount = currentMovie.movieEntity.voteCount,
            voteAverage = currentMovie.movieEntity.voteAverage,
            actors = currentMovie.actors.map {
                Actor(id = it.id, name = it.name, actorImage = it.actorImage, cast_id = it.castId)
            },
            genres = currentMovie.genres.map {
                Genre(id = it.id, name = it.name)
            }
        )
    }
}

sealed class MoviesDetailsState{
    class Success(val movie: Movie): MoviesDetailsState()
    class Error(val errorMessage: String): MoviesDetailsState()
    object Loading : MoviesDetailsState()
}