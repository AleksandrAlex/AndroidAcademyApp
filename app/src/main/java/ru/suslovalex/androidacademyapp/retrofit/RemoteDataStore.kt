package ru.suslovalex.androidacademyapp.retrofit

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.suslovalex.androidacademyapp.data.*

class RemoteDataStore {

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

    private suspend fun getGenres(): GenreResponse {
        return movieApi.getGenreResponse()
    }

    private suspend fun getActors(movieID: Long): ActorResponse {
        return movieApi.getActors(movieID)
    }

    private suspend fun getRuntime(movieID: Long): DetailsResponse {
        return movieApi.getRuntime(movieID)
    }

    suspend fun loadGenres(): List<Genre> {
        return getGenres().genres.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    suspend fun loadActors(movieID: Long): List<Actor> {
        val actors = getActors(movieID).cast.map { actor ->
            Actor(
                id = actor.id,
                name = actor.name,
                actorImage = if (actor.profilePath != null) MoviesApi.BASE_IMAGE_URL + actor.profilePath else null,
                cast_id = actor.castId
            )
        }
        Log.d("Actors ", actors.toString())
        return actors
    }

    suspend fun loadRuntime(movieID: Long): Int {
        return getRuntime(movieID).runtime
    }

    suspend fun getUpcomingMovies(): MoviesResponse {
        return movieApi.getUpcomingMovies()
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