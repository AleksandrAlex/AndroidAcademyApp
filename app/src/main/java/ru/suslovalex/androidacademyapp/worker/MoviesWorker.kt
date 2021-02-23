package ru.suslovalex.androidacademyapp.worker

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat
import androidx.work.*
import kotlinx.coroutines.flow.collect
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.data.entity.relation.MovieWithActorsAndGenres
import ru.suslovalex.androidacademyapp.notification.INotification
import ru.suslovalex.androidacademyapp.notification.MovieNotification
import ru.suslovalex.androidacademyapp.repository.MoviesRepository


@KoinApiExtension
class MoviesWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()
    private val notification = MovieNotification(context)

    @Suppress("UNREACHABLE_CODE")
    override suspend fun doWork(): Result {
        return try {
            val movies: List<Movie> = moviesRepository.loadUpcomingMovies()
            saveData(movies)
            sendNotification(movies)
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
    
    private fun sendNotification(movies: List<Movie>) {
        notification.createChannel()
        val mostRatingMovie = movies.maxByOrNull { it.voteAverage }
        mostRatingMovie?.let { notification.showNotification(it) }
    }

    private suspend fun saveData(movies: List<Movie>) {
        moviesRepository.saveDatesToDatabase(movies)
        Log.d("MoviesWorker", "$movies")
    }
}
