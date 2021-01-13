package ru.suslovalex.androidacademyapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.suslovalex.androidacademyapp.data.Genre
import ru.suslovalex.androidacademyapp.data.GenreResponse
import ru.suslovalex.androidacademyapp.data.MoviesResponse


private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "40efb58f57f8571c97c5a073353ebb32"

//https://api.themoviedb.org/3/movie/popular?api_key=40efb58f57f8571c97c5a073353ebb32&language=en-US&page=1
interface MoviesApi {
    @GET("movie/popular?api_key=40efb58f57f8571c97c5a073353ebb32&language=en-US&page=1")
    suspend fun getPopularMovies(): MoviesResponse

    @GET("movie/top_rated?api_key=40efb58f57f8571c97c5a073353ebb32&language=en-US&page=1")
    suspend fun getTopMovies(): MoviesResponse

    @GET("movie/upcoming?api_key=40efb58f57f8571c97c5a073353ebb32&language=ru-RU&page=1")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesResponse

    @GET("genre/movie/list?api_key=40efb58f57f8571c97c5a073353ebb32&language=en-US")
    suspend fun getGenres(): GenreResponse
}

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val movieApi: MoviesApi by lazy {
        retrofit.create(MoviesApi::class.java)
    }
}