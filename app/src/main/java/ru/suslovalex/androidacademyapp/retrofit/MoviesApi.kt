package ru.suslovalex.androidacademyapp.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import ru.suslovalex.androidacademyapp.data.*
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.API_KEY
import ru.suslovalex.androidacademyapp.retrofit.MoviesApi.Companion.BASE_URL


//https://api.themoviedb.org/3/movie/popular?api_key=40efb58f57f8571c97c5a073353ebb32&language=en-US&page=1
// https://api.themoviedb.org/3/movie/464052/credits?api_key=40efb58f57f8571c97c5a073353ebb32&language=ru-ru
interface MoviesApi {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "40efb58f57f8571c97c5a073353ebb32"
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
       // https://image.tmdb.org/t/p/w500/xu1l9WmNY1XYZyJ3M2gvGqCCDGS.jpg
    }

    @GET("movie/popular?&language=en-US&page=1")
    suspend fun getPopularMovies(): MoviesResponse

    @GET("movie/top_rated?&language=en-US&page=1")
    suspend fun getTopMovies(): MoviesResponse

    @GET("movie/upcoming?&language=ru-RU&page=1")
    suspend fun getUpcomingMovies(): MoviesResponse

    @GET("genre/movie/list?&language=ru-RU")
    suspend fun getGenreResponse(): GenreResponse

    @GET("movie/{movie_id}/credits?&language=ru-RU")
    suspend fun getActors(@Path("movie_id") movieID: Long): ActorResponse

    @GET("movie/{movie_id}?&language=en-US")
    suspend fun getRuntime(@Path("movie_id") movieID: Long): DetailsResponse
}

object RemoteDataStore {

    private val movieApi: MoviesApi by lazy {
        retrofit.create(MoviesApi::class.java)
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(MoviesApiKeyInterceptor())
        .build()

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    suspend fun getUpcomingMovies(): MoviesResponse {
        return movieApi.getUpcomingMovies()
    }

    suspend fun getGenres(): GenreResponse {
        return movieApi.getGenreResponse()
    }

    suspend fun getActors(movieID: Long): ActorResponse{
        return movieApi.getActors(movieID)
    }

    suspend fun getRuntime(movieID: Long): DetailsResponse{
        return movieApi.getRuntime(movieID)
    }
}

class MoviesApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val request = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val url = originalRequest.newBuilder().url(request).build()
        return chain.proceed(url)
    }
}
