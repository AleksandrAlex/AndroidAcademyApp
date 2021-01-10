package ru.suslovalex.androidacademyapp.viewmodel

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.loadMovies
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult

class MoviesListViewModel(private val checker: MovieChecker) : ViewModel() {

    private val _state = MutableLiveData<MoviesListState>()
    val moviesListState: LiveData<MoviesListState>
        get() = _state


    fun getMovies(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(MoviesListState.Loading)
        val movies = loadMovies(context)
        val checkResult = checker.loadMoviesList(movies)
        val newState = when (checkResult) {
            is MovieResponseResult.Success -> {
                MoviesListState.Success(movies)
            }
            is MovieResponseResult.Error -> MoviesListState.Error("Error!")
        }
        _state.postValue(newState)
    }
}

sealed class MoviesListState {
    data class Error(val errorMessage: String) : MoviesListState()
    object Loading : MoviesListState()
    data class Success(val movies: List<Movie>) : MoviesListState()
}