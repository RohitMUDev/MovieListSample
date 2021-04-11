package com.example.movielist.ui.movie

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.movielist.data.local.sharedPreference.getTitle
import com.example.movielist.data.model.Content
import com.example.movielist.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieListViewModel(
        context: Application,
        private val movieRepository: MovieRepository,
        private val preferences: SharedPreferences,
) : AndroidViewModel(context) {

    private var _updateTile :  MutableLiveData<String> = MutableLiveData()
    var updateTile : LiveData<String> = _updateTile

    private var _moviesList :  MutableLiveData<List<Content>> = MutableLiveData()
    var moviesList : LiveData<List<Content>> = _moviesList
    fun getMoviesList() = movieRepository.getResult()


    fun setTitle (){
        _updateTile.value = (preferences.getTitle())
    }


    fun getMoviesFromDB(){

        viewModelScope.launch(Dispatchers.IO) {
            _moviesList.postValue(movieRepository.getMoviesFromDB())
        }
    }

    companion object {

        const val PAGE_SIZE = 20
    }
}