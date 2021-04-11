package com.example.movielist

import android.app.Application
import com.example.movielist.di.databaseModule
import com.example.movielist.di.preferencesModule
import com.example.movielist.di.repositoryModule
import com.example.movielist.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieListApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieListApp)
            modules(listOf(viewModelModule, repositoryModule, preferencesModule, databaseModule))
        }
    }

}