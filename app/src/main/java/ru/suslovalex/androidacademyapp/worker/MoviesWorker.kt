package ru.suslovalex.androidacademyapp.worker

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat
import androidx.work.*
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.suslovalex.androidacademyapp.data.Movie
import ru.suslovalex.androidacademyapp.repository.MoviesRepository


@KoinApiExtension
class MoviesWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {

    private val moviesRepository: MoviesRepository by inject()

    override suspend fun doWork(): Result {
        return try {
            val movies: List<Movie> = moviesRepository.loadUpcomingMovies()
            deleteData()
            saveData(movies)
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

    private suspend fun deleteData() {
        moviesRepository.deleteAllDatabase()
    }

    private suspend fun saveData(movies: List<Movie>) {
        moviesRepository.saveDatesToDatabase(movies)
        Log.d("MoviesWorker","$movies")
    }
}
