package com.example.movielist.ui.movie

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.R
import com.example.movielist.data.local.sharedPreference.SharedPreferenceStringLiveData
import com.example.movielist.databinding.MovieListFragmentBinding
import com.example.movielist.util.SpacesItemDecoration
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel


class MovieListFragment : Fragment() {


    private val movieListViewModel: MovieListViewModel by viewModel()
    private var _movieListFragmentBinding: MovieListFragmentBinding? = null
    private val preference: SharedPreferences by inject()

    private val binding get() = _movieListFragmentBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _movieListFragmentBinding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MoviePagerAdapter(requireContext())
        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)



        binding.apply {
            rvMovies.setHasFixedSize(true)
            rvMovies.adapter = adapter
            rvMovies.layoutManager = manager
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.corner)
            rvMovies.addItemDecoration(SpacesItemDecoration(spacingInPixels))


            ibtSearch.setOnClickListener {
                val action = MovieListFragmentDirections.actionMovieListFragmentToSearchFragment()
                Navigation.findNavController(requireView()).navigate(action)
            }

            ibtBack.setOnClickListener {

                requireActivity().onBackPressed()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            movieListViewModel.getMoviesList().collectLatest { pagingData ->
                movieListViewModel.setTitle()
                adapter.submitData(pagingData)
            }
        }

        movieListViewModel.updateTile.observe(viewLifecycleOwner) {
            it.let {
                binding.tvTitle.text = it
            }
        }

        val sharedPreferences = SharedPreferenceStringLiveData(preference, "title", "")
        sharedPreferences.observe(viewLifecycleOwner) {

            binding.tvTitle.text = it
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _movieListFragmentBinding = null
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        setColumns(
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                7
            } else 3
        )
        super.onConfigurationChanged(newConfig)
    }

    private fun setColumns(count: Int) {
        binding.rvMovies.layoutManager =
            GridLayoutManager(activity, count, GridLayoutManager.VERTICAL, false)
    }


}