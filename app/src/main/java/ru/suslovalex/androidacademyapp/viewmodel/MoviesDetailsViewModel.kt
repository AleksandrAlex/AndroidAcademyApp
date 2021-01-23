package ru.suslovalex.androidacademyapp.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.Result
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult

class MoviesDetailsViewModel(private val checker: MovieChecker): ViewModel() {

    private val _state = MutableLiveData<MoviesDetailsState>()
    val state: LiveData<MoviesDetailsState>
    get() = _state

    fun getMovie(bundle: Bundle) = viewModelScope.launch (Dispatchers.IO){
        _state.postValue(MoviesDetailsState.Loading)
        val currentMovie = bundle.getParcelable<Movie>("movie")
        val newState = when(checker.loadMovie(currentMovie)){
            MovieResponseResult.Success -> currentMovie?.let { MoviesDetailsState.Success(it) }
            MovieResponseResult.Error -> MoviesDetailsState.Error("Error!")
        }
        _state.postValue(newState)
    }
}

sealed class MoviesDetailsState{
    class Success(val movie: Movie): MoviesDetailsState()
    class Error(val errorMessage: String): MoviesDetailsState()
    object Loading : MoviesDetailsState()
}