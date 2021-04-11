package com.example.movielist.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.paging.PagingSource
import com.example.movielist.data.local.db.MovieDao
import com.example.movielist.data.local.sharedPreference.setTitle
import com.example.movielist.data.model.Content
import java.io.IOException

private const val START_INDEX = 1

class MovieListPagingSource(private val context: Context, private val repository: MovieRepository, val preferences: SharedPreferences, val  movieDao: MovieDao) :
        PagingSource<Int, Content>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {

        val pageNumber: Int = params.key ?: START_INDEX
        Log.e("PageNumber " , "$pageNumber")
        return try {
            val response = repository.getMovieList(context = context, pageNumber = pageNumber)
            response?.page?.title?.let { preferences.setTitle(it) }
            val data = response?.page?.contentItems?.content
            if(data!!.isNotEmpty()){
                if(pageNumber == START_INDEX){
                    movieDao.deleteFromEntries()
                }
                movieDao.insertAllMovies(data)
            }
            LoadResult.Page(
                    data = data,
                    prevKey = if (pageNumber == START_INDEX) null else pageNumber - 1,
                    nextKey = if (response.page?.contentItems?.content.isNullOrEmpty()) {
                        null
                    } else pageNumber + 1
            )


        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }


}