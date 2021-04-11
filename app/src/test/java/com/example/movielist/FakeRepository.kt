package com.example.movielist

import android.content.Context
import android.content.SharedPreferences
import com.example.movielist.data.local.db.MovieDao
import com.example.movielist.data.model.Content
import com.example.movielist.data.model.ContentItems
import com.example.movielist.data.model.MovieList
import com.example.movielist.data.model.Page
import com.example.movielist.data.repository.MovieRepository

class FakeRepository ( context: Context, val movieDao: MovieDao, val preferences: SharedPreferences
) : MovieRepository(context, movieDao, preferences){


    private fun  createDummyData(pageNumber : Int): MovieList {


        val content1 = Content(
            name = "test1",
            posterImage = "poster1.jpg",
            id = 1,
        )
        val content2 = Content(
            name = "test1",
            posterImage = "poster1.jpg",
            id = 1,
        )

        val content3 = Content(
            name = "test1",
            posterImage = "poster1.jpg",
            id = 1,
        )

        val content4 = Content(
            name = "test1",
            posterImage = "poster1.jpg",
            id = 1,
        )
        val items = ArrayList<Content>()



        when (pageNumber) {

            1 ->{
                items.add(content1)
                items.add(content2)
                items.add(content3)
                items.add(content4)
                items.add(content2)
                items.add(content3)
                items.add(content4)
            }

            2-> {
                items.add(content1)
                items.add(content2)
                items.add(content3)
                items.add(content4)
            }

            3-> {
                items.add(content1)
                items.add(content2)
            }
        }




        val contentItems = ContentItems(
            content =  items
        )
        val page = Page(
            title = "Romantic comedy",
            pageSize = "20",
            contentItems = contentItems,
            pageNum = "1",
            totalContentItems = "54"
        )
        return MovieList(page = page)
    }

    override suspend fun getMovieList(context: Context, pageNumber: Int): MovieList? {

            return  createDummyData(pageNumber)
    }

    override suspend fun getMovies(): List<Content> {
        return createDummyData(2).page!!.contentItems!!.content!!
    }

    override suspend fun getMoviesFromDB(): List<Content> {
        return createDummyData(2).page!!.contentItems!!.content!!
    }




}