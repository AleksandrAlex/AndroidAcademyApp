package ru.suslovalex.androidacademyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.suslovalex.androidacademyapp.repository.Repository
import ru.suslovalex.androidacademyapp.domain.MovieChecker

class MoviesListViewModelProviderFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(MovieChecker(), Repository())
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
