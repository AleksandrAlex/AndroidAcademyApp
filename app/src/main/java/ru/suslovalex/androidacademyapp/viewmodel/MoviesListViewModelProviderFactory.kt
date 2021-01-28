package ru.suslovalex.androidacademyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.suslovalex.androidacademyapp.db.MoviesDatabase
import ru.suslovalex.androidacademyapp.repository.MoviesRepository
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.retrofit.RemoteDataStore



//class MoviesListViewModelProviderFactory : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
//        MoviesListViewModel::class.java -> MoviesListViewModel(MovieChecker(),
//            MoviesRepository(RemoteDataStore, MoviesDatabase(MovieApp().context)))
//        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
//    } as T
//}
