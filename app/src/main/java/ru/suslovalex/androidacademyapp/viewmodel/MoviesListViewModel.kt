package ru.suslovalex.androidacademyapp.viewmodel


import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.suslovalex.androidacademyapp.data.Actor
import ru.suslovalex.androidacademyapp.data.Genre
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.entity.ActorEntity
import ru.suslovalex.androidacademyapp.data.entity.GenreEntity
import ru.suslovalex.androidacademyapp.data.entity.relation.MovieWithActorsAndGenres
import ru.suslovalex.androidacademyapp.repository.MoviesRepository
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.domain.MovieResponseResult


class MoviesListViewModel(
    private val checker: MovieChecker,
    private val moviesRepository: MoviesRepository
) : ViewModel() {


    private val _state = MutableLiveData<MoviesListState>()
    val moviesListState: LiveData<MoviesListState>
        get() = _state

    init {
        readMoviesFromDatabase()
    }

    private fun loadUpcomingMovies() = viewModelScope.launch {
        deleteAllDatabase()
        _state.postValue(MoviesListState.Loading)
        val movies = moviesRepository.loadUpcomingMovies()

        val newState = when (checker.loadMoviesList(movies)) {
            MovieResponseResult.Success -> {
                saveDatesToDatabase(movies)
                MoviesListState.Success(movies)
            }
            MovieResponseResult.Error -> MoviesListState.Error("Error!")
        }
        _state.postValue(newState)
    }

    private fun saveDatesToDatabase(movies: List<Movie>) = viewModelScope.launch {
        moviesRepository.saveDatesToDatabase(movies)
    }

    private fun deleteAllDatabase() = viewModelScope.launch {
        moviesRepository.deleteAllDatabase()
    }

    private fun convertDataToMovies(listMoviesWithActorsAndGenres: List<MovieWithActorsAndGenres>): List<Movie> {
        return listMoviesWithActorsAndGenres.map { listData ->
            Movie(
                id = listData.movieEntity.id,
                adult = if (listData.movieEntity.adult) "16+" else "13+",
                backdropPath = listData.movieEntity.backdropPath,
                runtime = listData.movieEntity.runtime,
                overview = listData.movieEntity.overview,
                posterPath = listData.movieEntity.posterPath,
                releaseDate = listData.movieEntity.releaseDate,
                title = listData.movieEntity.title,
                voteAverage = listData.movieEntity.voteAverage,
                voteCount = listData.movieEntity.voteCount,
                actors = convertDataToActors(listData.actors),
                genres = convertDataToGenres(listData.genres)
            )
        }
    }


    private fun convertDataToGenres(genres: List<GenreEntity>): List<Genre> {
        return genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    private fun convertDataToActors(actors: List<ActorEntity>): List<Actor> {
        return actors.map {
            Actor(
                id = it.id,
                name = it.name,
                actorImage = it.actorImage,
                cast_id = it.castId
            )
        }
    }

    private fun readMoviesFromDatabase() = viewModelScope.launch(Dispatchers.IO) {
        _state.postValue(MoviesListState.Loading)
        moviesRepository.readMoviesFromDatabase().collect {
            val movies = convertDataToMovies(it)
            val newState = when (checker.loadMoviesList(movies)) {
                MovieResponseResult.Success -> {
                    MoviesListState.Success(movies)
                }
                MovieResponseResult.Error -> {
                    loadUpcomingMovies()
                    MoviesListState.Error("Loading...")
                }
            }
            _state.postValue(newState)
        }

    }

}

sealed class MoviesListState {
    data class Error(val errorMessage: String) : MoviesListState()
    object Loading : MoviesListState()
    data class Success(val movies: List<Movie>) : MoviesListState()
}