package ru.suslovalex.androidacademyapp.view

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ru.suslovalex.androidacademyapp.db.MoviesDao
import ru.suslovalex.androidacademyapp.db.MoviesDatabase
import ru.suslovalex.androidacademyapp.domain.MovieChecker
import ru.suslovalex.androidacademyapp.repository.MoviesRepository
import ru.suslovalex.androidacademyapp.retrofit.RemoteDataStore
import ru.suslovalex.androidacademyapp.viewmodel.MoviesDetailsViewModel
import ru.suslovalex.androidacademyapp.viewmodel.MoviesListViewModel

class MovieApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        val movieListModule = module {
            viewModel { MoviesListViewModel(get(), get()) }
        }

        val movieDetailModule = module {
            viewModel { MoviesDetailsViewModel(get()) }
        }

        val appModule = module {
            single { MoviesDatabase(get()) }
            single { RemoteDataStore() }
            single { MovieChecker() }
            single { MoviesRepository(get(), get()) }
        }

        startKoin{
            androidContext(this@MovieApplication)
            modules(movieListModule, movieDetailModule, appModule)
        }
    }
}