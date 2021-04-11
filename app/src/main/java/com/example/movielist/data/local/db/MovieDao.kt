package com.example.movielist.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movielist.data.model.Content

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies :  List<Content>)

    @Query("SELECT * FROM Content")
    suspend fun getMovies() : List<Content>


    @Query("DELETE FROM Content")
    suspend fun deleteFromEntries()
}