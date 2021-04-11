package com.example.movielist.ui.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.data.model.Content
import com.example.movielist.databinding.ItemMovieListBinding
import com.example.movielist.util.setPosterImage


class MoviePagerAdapter(val context: Context) :
    PagingDataAdapter<Content, MoviePagerAdapter.MovieViewHolder>(
        PHOTO_COMPARATOR
    ) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Content>() {
            override fun areItemsTheSame(oldItem: Content, newItem: Content) =
                false

            override fun areContentsTheSame(oldItem: Content, newItem: Content) =
                oldItem == newItem
        }
    }

    inner class MovieViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(content: Content) {
            binding.apply {
                ivPoster.setPosterImage(content.posterImage!!, context = context)
                tvTitle.text = content.name
            }

        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val data = getItem(position)

        data.let { movie ->
            holder.bind(movie!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}