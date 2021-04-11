package com.example.movielist.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.movielist.data.api.MoviesApiHelper
import com.example.movielist.data.local.db.MovieDao
import com.example.movielist.data.model.Content
import com.example.movielist.data.model.MovieList
import com.example.movielist.ui.movie.MovieListViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

private const val RESPONSE_FILE_NAME_1 = "content_page1.json"
private const val RESPONSE_FILE_NAME_2 = "content_page2.json"
private const val RESPONSE_FILE_NAME_3 = "content_page3.json"
open class MovieRepository(val context : Context, private val movieDao: MovieDao, private  val preferences: SharedPreferences) : MoviesApiHelper {

    override suspend fun getMovieList(context: Context, pageNumber: Int): MovieList? {

        val response = when (pageNumber) {

            1 -> {
                getJsonResponse(context, RESPONSE_FILE_NAME_1)
            }

            2 -> {
                getJsonResponse(context, RESPONSE_FILE_NAME_2)

            }

            3 -> {
                getJsonResponse(context, RESPONSE_FILE_NAME_3)

            }

            else -> return null
        }

        response.let {

            val gson = Gson()
            val listOfMovies = object : TypeToken<MovieList>() {}.type
            return gson.fromJson<MovieList>(it, listOfMovies)

        }
    }

    override suspend fun getMovies(): List<Content> {

        return emptyList()
    }

    override suspend fun getMoviesFromDB(): List<Content> {
        return movieDao.getMovies()
    }


    private fun getJsonResponse(context: Context, fileName: String): String? {

        val responseString: String
        try {
            responseString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return responseString

    }


    fun getResult() =

        Pager(
            config = PagingConfig(
                pageSize = MovieListViewModel.PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                MovieListPagingSource(context = context, repository = this, preferences = preferences, movieDao = movieDao)
            }
        ).flow


}