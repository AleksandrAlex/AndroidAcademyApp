package ru.suslovalex.androidacademyapp.view

import android.app.Application
import androidx.work.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.suslovalex.androidacademyapp.db.MoviesDatabase
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.repository.MoviesRepository
import ru.suslovalex.androidacademyapp.retrofit.RemoteDataStore
import ru.suslovalex.androidacademyapp.viewmodel.MoviesDetailsViewModel
import ru.suslovalex.androidacademyapp.viewmodel.MoviesListViewModel
import ru.suslovalex.androidacademyapp.worker.MoviesWorker
import java.util.concurrent.TimeUnit


class MovieApplication: Application() {

    @KoinApiExtension
    override fun onCreate() {
        super.onCreate()

        setPeriodicWorkRequest()

        val movieListModule = module {
            viewModel { MoviesListViewModel(get(), get()) }
        }

        val movieDetailModule = module {
            viewModel { MoviesDetailsViewModel(get()) }
        }

        val appModule = module {
            single { MoviesDatabase(get()) }
            single { RemoteDataStore() }
            factory { MovieChecker() }
            single { MoviesRepository(get(), get()) }
        }

        startKoin{
            androidContext(this@MovieApplication)
            modules(movieListModule, movieDetailModule, appModule)
        }
    }

    @KoinApiExtension
    private fun setPeriodicWorkRequest() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(MoviesWorker::class.java, 8, TimeUnit.HOURS)
                .setConstraints(constraint)
                .build()

//        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MoviesWorker::class.java).setConstraints(constraint).build()

        WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
//        WorkManager.getInstance(applicationContext).enqueue(oneTimeWorkRequest)
    }
}