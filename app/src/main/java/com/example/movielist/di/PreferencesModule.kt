package com.example.movielist.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val preferencesModule = module {
    single { provideDevicePreferences(androidApplication()) }
}

const val DEVICE_PREFS_FILE_KEY = "com.example.movielist.device_preferences"

private fun provideDevicePreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(DEVICE_PREFS_FILE_KEY, Context.MODE_PRIVATE)
