package ru.suslovalex.androidacademyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult

class MoviesDetailsViewModel(private val checker: MovieChecker): ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
    get() = _movie

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
    get() = _state

    suspend fun setMovie(currentMovie: Movie) = viewModelScope.launch (Dispatchers.IO){
        _state.postValue(State.Loading())
        val checkResult = checker.loadMovie(currentMovie)
        val newState = when(checkResult){
            is MovieResponseResult.Success ->{
                _movie.postValue(currentMovie)
                State.Success()
            }
            is MovieResponseResult.Error -> State.Error()
        }
        _state.postValue(newState)
    }
}