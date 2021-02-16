package ru.suslovalex.androidacademyapp.retrofit


import retrofit2.http.GET
import retrofit2.http.Path
import ru.suslovalex.androidacademyapp.data.*


//https://api.themoviedb.org/3/movie/popular?api_key=40efb58f57f8571c97c5a073353ebb32&language=en-US&page=1
// https://api.themoviedb.org/3/movie/464052/credits?api_key=40efb58f57f8571c97c5a073353ebb32&language=ru-ru
interface MoviesApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "40efb58f57f8571c97c5a073353ebb32"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
       // https://image.tmdb.org/t/p/w500/xu1l9WmNY1XYZyJ3M2gvGqCCDGS.jpg
        //https://api.themoviedb.org/3/genre/movie/list?api_key=40efb58f57f8571c97c5a073353ebb32&language=ru-RU
        //https://api.themoviedb.org/3/movie/464052/credits?api_key=40efb58f57f8571c97c5a073353ebb32&language=ru-RU
    }

    @GET("movie/popular?&language=en-US&page=1")
    suspend fun getPopularMovies(): MoviesResponse

    @GET("movie/top_rated?&language=en-US&page=1")
    suspend fun getTopMovies(): MoviesResponse

    @GET("movie/upcoming?&language=ru-RU&page=1")
    suspend fun getUpcomingMovies(): MoviesResponse

    @GET("genre/movie/list?&language=ru-RU")
    suspend fun getGenreResponse(): GenreResponse

    @GET("movie/{movie_id}/credits?&language=en-EN")
    suspend fun getActors(@Path("movie_id") movieID: Long): ActorResponse

    @GET("movie/{movie_id}?&language=en-US")
    suspend fun getRuntime(@Path("movie_id") movieID: Long): DetailsResponse
}


