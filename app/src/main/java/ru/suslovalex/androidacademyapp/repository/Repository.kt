package ru.suslovalex.androidacademyapp.repository

import ru.suslovalex.androidacademyapp.data.MoviesResponse
import ru.suslovalex.androidacademyapp.retrofit.RetrofitInstance

class Repository {

    suspend fun getUpcomingMovies(): MoviesResponse{
        return RetrofitInstance.movieApi.getUpcomingMovies()
    }
}