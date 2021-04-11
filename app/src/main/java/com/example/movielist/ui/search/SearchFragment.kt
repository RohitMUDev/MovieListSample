package com.example.movielist.ui.search

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movielist.R
import com.example.movielist.databinding.SearchFragmentBinding
import com.example.movielist.ui.movie.MovieListFragmentDirections
import com.example.movielist.ui.movie.MovieListViewModel
import com.example.movielist.util.SpacesItemDecoration
import com.example.movielist.util.hideKeyboard
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModel()

    private var _searchFragmentBinding: SearchFragmentBinding? = null

    private val binding get() = _searchFragmentBinding

    private lateinit var adapter: SearchAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _searchFragmentBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter(requireContext(), ArrayList())
        val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        movieListViewModel.getMoviesFromDB()


        binding!!.apply {
            rvItems.adapter = adapter
            rvItems.layoutManager = manager
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.corner)
            rvItems.addItemDecoration(SpacesItemDecoration(spacingInPixels))

            ibtBack.setOnClickListener {
                requireView().hideKeyboard()
                Navigation.findNavController(requireView()).navigateUp()
            }

            imageButton.setOnClickListener {

                etQuery.isActivated = true
                etQuery.isPressed = true
            }
        }

        movieListViewModel.moviesList.observe(viewLifecycleOwner) {

            adapter.addContentFromDB(it)
        }


    }


    override fun onResume() {
        super.onResume()
        binding!!.etQuery.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding!!.etQuery.removeTextChangedListener(textWatcher)
    }

    private val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                if (text.trim().length >= 3) {
                    adapter.filter.filter(text.trim())
                } else {

                    adapter.filter.filter("")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _searchFragmentBinding = null
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
        binding!!.rvItems.layoutManager =
            GridLayoutManager(activity, count, GridLayoutManager.VERTICAL, false)
    }

}