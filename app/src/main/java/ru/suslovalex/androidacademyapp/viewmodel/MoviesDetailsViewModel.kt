package ru.suslovalex.androidacademyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.suslovalex.androidacademyapp.data.Movie

class MoviesDetailsViewModel(choseMovie: Movie): ViewModel() {
    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
    get() = _movie

    init {
        _movie.value = choseMovie
    }
}