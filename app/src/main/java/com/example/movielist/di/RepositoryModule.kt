package com.example.movielist.di

import com.example.movielist.data.repository.MovieRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoryModule = module {

    single {

        MovieRepository(androidApplication(), get(), get())
    }

}