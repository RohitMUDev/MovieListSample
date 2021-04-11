package com.example.movielist.data.api

import android.content.Context
import com.example.movielist.data.model.Content
import com.example.movielist.data.model.MovieList

interface MoviesApiHelper {

    suspend fun getMovieList(context: Context, pageNumber : Int) : MovieList?

    suspend fun getMovies() :  List<Content>

    suspend fun getMoviesFromDB() :  List<Content>

}