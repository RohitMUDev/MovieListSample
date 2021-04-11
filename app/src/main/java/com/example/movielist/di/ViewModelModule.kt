package com.example.movielist.di

import com.example.movielist.ui.movie.MovieListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {

        MovieListViewModel(androidApplication(),
                get(), get())

    }

}