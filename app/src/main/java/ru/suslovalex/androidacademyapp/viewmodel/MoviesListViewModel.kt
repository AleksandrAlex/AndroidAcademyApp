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

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    suspend fun getMovies(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(State.Loading())
        val movies = loadMovies(context)
        val checkResult = checker.loadMoviesList(movies)
        val newState = when (checkResult) {
            is MovieResponseResult.Success -> {
                _movieList.postValue(movies)
                State.Success()
            }
            is MovieResponseResult.Error -> State.Error()
        }
        _state.postValue(newState)
    }
}

sealed class State {
    class Error() : State()
    class Loading() : State()
    class Success() : State()
}