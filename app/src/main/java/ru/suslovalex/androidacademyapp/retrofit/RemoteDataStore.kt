package ru.suslovalex.androidacademyapp.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.suslovalex.androidacademyapp.data.ActorResponse
import ru.suslovalex.androidacademyapp.data.DetailsResponse
import ru.suslovalex.androidacademyapp.data.GenreResponse
import ru.suslovalex.androidacademyapp.data.MoviesResponse

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
            .baseUrl(MoviesApi.BASE_URL)
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

    suspend fun getActors(movieID: Long): ActorResponse {
        return movieApi.getActors(movieID)
    }

    suspend fun getRuntime(movieID: Long): DetailsResponse {
        return movieApi.getRuntime(movieID)
    }
}

class MoviesApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val request = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", MoviesApi.API_KEY)
            .build()
        val url = originalRequest.newBuilder().url(request).build()
        return chain.proceed(url)
    }
}