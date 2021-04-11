package com.example.movielist.ui.movie

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.movielist.data.local.db.MovieDao
import com.example.movielist.data.local.db.MovieDataBase
import com.example.movielist.data.model.Content
import com.example.movielist.data.model.ContentItems
import com.example.movielist.data.model.MovieList
import com.example.movielist.data.model.Page
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class MovieListViewModelTest {

    private lateinit var movieDao: MovieDao
    private lateinit var db: MovieDataBase

    lateinit var instrumentationContext: Context



    @Before
    fun setUp() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(instrumentationContext, MovieDataBase::class.java).build()
        movieDao = db.movieDao()

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }


    @Test
    @Throws(IOException::class)
    fun insertMovies() = runBlocking() {

        val data = createDummyData(1).page!!.contentItems!!.content
        movieDao.insertAllMovies(data!!)
        val result =  movieDao.getMovies()
        assertEquals("test1", result[0].name)
   }


    @Test
    @Throws(IOException::class)
    fun deleteMovies() = runBlocking() {

        val data = createDummyData(1).page!!.contentItems!!.content
        movieDao.insertAllMovies(data!!)
        val result =  movieDao.getMovies()
        assertEquals("test1", result[0].name)

        movieDao.deleteFromEntries()
        val newResult =  movieDao.getMovies()
        assertEquals(0, newResult.size)

    }

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






}