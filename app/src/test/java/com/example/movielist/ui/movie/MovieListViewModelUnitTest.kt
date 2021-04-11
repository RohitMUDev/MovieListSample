package com.example.movielist.ui.movie

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.movielist.FakeRepository
import com.example.movielist.TestCoroutineRule
import com.example.movielist.data.local.db.MovieDao
import com.example.movielist.data.repository.MovieRepository
import com.example.movielist.di.*
import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MovieListViewModelUnitTest : KoinTest {

    private lateinit  var movieRepository : MovieRepository
    private val movieDao : MovieDao by inject()
    private lateinit var fakeRepository: FakeRepository
    private val spMockBuilder = SPMockBuilder()
    private lateinit var devicePreference: SharedPreferences

    @Mock
    private lateinit var context: Application

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
    @Before
    fun setUp() {

        MockitoAnnotations.initMocks(this)
        startKoin {
            androidContext(context)
            modules(viewModelModule, repositoryModule, preferencesModule, databaseModule)
        }
        devicePreference = spMockBuilder.createContext()
            .getSharedPreferences(DEVICE_PREFS_FILE_KEY, Context.MODE_PRIVATE)
        fakeRepository =  FakeRepository(context, movieDao, devicePreference)
        movieRepository = MovieRepository(context, movieDao, devicePreference)


    }

    @After
    fun tearDown() {

        stopKoin()
    }

    @Test
    fun `test get response from api for page 1 `(){
        runBlockingTest {
         val data =    fakeRepository.getMovieList(context, pageNumber = 1)
         assertEquals(7, data!!.page!!.contentItems!!.content!!.size)


        }

    }

    @Test
    fun `test get response from api for page 2 `(){
        runBlockingTest {
            val data =    fakeRepository.getMovieList(context, pageNumber = 2)
            assertEquals(4, data!!.page!!.contentItems!!.content!!.size)


        }

    }

    @Test
    fun `test get response from api for page 3 `(){
        runBlockingTest {
            val data =    fakeRepository.getMovieList(context, pageNumber = 3)
            assertEquals(2, data!!.page!!.contentItems!!.content!!.size)
        }

    }

}