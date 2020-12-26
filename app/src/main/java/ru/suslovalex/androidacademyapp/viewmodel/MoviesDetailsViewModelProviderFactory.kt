package ru.suslovalex.androidacademyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.suslovalex.androidacademyapp.data.Movie

class MoviesDetailsViewModelProviderFactory(private val choseMovie: Movie): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MoviesDetailsViewModel(choseMovie) as T
    }
}