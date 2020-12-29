package ru.suslovalex.androidacademyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.suslovalex.androidacademyapp.domain.MovieChecker

class MovieDetailsViewModelProviderFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(MovieChecker())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
