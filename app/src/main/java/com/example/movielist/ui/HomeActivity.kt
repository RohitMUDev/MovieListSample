package com.example.movielist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movielist.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    private var _homeActivityMainBinding: ActivityMainBinding? = null
    private val binding get() = _homeActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _homeActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        _homeActivityMainBinding = null
    }
}