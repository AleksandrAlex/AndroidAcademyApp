package ru.suslovalex.androidacademyapp.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.repository.Repository
import ru.suslovalex.androidacademyapp.data.GenreResponse
import ru.suslovalex.androidacademyapp.data.MoviesResponse
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult
import ru.suslovalex.androidacademyapp.retrofit.RetrofitInstance

class MoviesListViewModel(private val checker: MovieChecker, private val repository: Repository) : ViewModel() {

    private val _state = MutableLiveData<MoviesListState>()
    val moviesListState: LiveData<MoviesListState>
        get() = _state

    private lateinit var genreResponse: GenreResponse

    init {
        getGenres()
        getPopularMovies()
    }

    private fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(MoviesListState.Loading)
        val moviesResponse = repository.getUpcomingMovies()
        val newState = when (checker.loadMoviesList(moviesResponse)) {
            MovieResponseResult.Success -> {
                MoviesListState.Success(moviesResponse)
            }
            MovieResponseResult.Error -> MoviesListState.Error("Error!")
        }
        _state.postValue(newState)
    }

    private fun getGenres() = viewModelScope.launch (Dispatchers.IO){
        genreResponse = RetrofitInstance.movieApi.getGenres()
        Log.d("GENRES ", genreResponse.genres.toString())
    }
}

sealed class MoviesListState {
    data class Error(val errorMessage: String) : MoviesListState()
    object Loading : MoviesListState()
    data class Success(val moviesResponse: MoviesResponse) : MoviesListState()
}