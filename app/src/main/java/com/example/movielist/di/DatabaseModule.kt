package com.example.movielist.di

import android.app.Application
import androidx.room.Room
import com.example.movielist.data.local.db.MovieDao
import com.example.movielist.data.local.db.MovieDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {
    fun provideDatabase(application: Application): MovieDataBase {
        return Room.databaseBuilder(
            application,
            MovieDataBase::class.java, "MoviesDB"
        ).build()
    }

    fun provideOneCardDao(database: MovieDataBase): MovieDao {
        return database.movieDao()
    }

    single { provideDatabase(application = androidApplication()) }
    single { provideOneCardDao(get()) }
}
