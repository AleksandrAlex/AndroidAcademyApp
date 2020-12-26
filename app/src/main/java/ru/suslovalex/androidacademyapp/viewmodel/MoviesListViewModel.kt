package ru.suslovalex.androidacademyapp.viewmodel

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.loadMovies

class MoviesListViewModel : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
    get() = _movieList

      suspend fun getMovies(context: Context) = withContext(Dispatchers.IO){
        _movieList.postValue(loadMovies(context))
    }
}