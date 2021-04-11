package com.example.movielist.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movielist.data.model.Content

@Database(
    entities = [Content::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDataBase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}