package ru.suslovalex.androidacademyapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.repository.MoviesRepository
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult

class MoviesListViewModel(private val checker: MovieChecker, private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private val _state = MutableLiveData<MoviesListState>()
    val moviesListState: LiveData<MoviesListState>
        get() = _state


    init {
            loadGenres()
            loadUpcomingMovies()
    }

    private fun loadUpcomingMovies() = viewModelScope.launch {
        _state.postValue(MoviesListState.Loading)
        val movies = moviesRepository.loadUpcomingMovies()
        val newState = when (checker.loadMoviesList(movies)) {
            MovieResponseResult.Success -> {
                MoviesListState.Success(movies)
            }
            MovieResponseResult.Error -> MoviesListState.Error("Error!")
        }
        _state.postValue(newState)
    }

    private fun loadGenres() = viewModelScope.launch {
        val genreResponse = moviesRepository.loadGenres()
        Log.d("GENRES ", genreResponse.toString())
    }
}

sealed class MoviesListState {
    data class Error(val errorMessage: String) : MoviesListState()
    object Loading : MoviesListState()
    data class Success(val movies: List<Movie>) : MoviesListState()
}