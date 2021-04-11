package com.example.movielist.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.movielist.data.model.Content
import com.example.movielist.databinding.ItemMovieListBinding
import com.example.movielist.util.setPosterImage


class SearchAdapter(val context: Context, var contentList: ArrayList<Content>) :
    RecyclerView.Adapter<SearchAdapter.MovieViewHolder>(),
    Filterable {

    private var moviesFromDB: ArrayList<Content> = ArrayList()
    private var searchTerm = ""

    inner class MovieViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(content: Content?) {
            binding.apply {
                ivPoster.setPosterImage(content?.posterImage!!, context)
                tvTitle.text = content.name
            }

        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bind(content = contentList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contentList.size
    }

    fun addContent(item: Content) {
        contentList.add(item)
    }

    fun addContentFromDB(item: List<Content>) {
        moviesFromDB.addAll(item)
    }

    fun clearContent() {
        contentList.clear()
        notifyDataSetChanged()

    }

    override fun getFilter(): Filter {
        return searchFilter
    }

    private val searchFilter by lazy {
        object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                searchTerm = constraint?.trim().toString()
                val filteredList: MutableList<Content> = arrayListOf()
                if (constraint == null || constraint.isEmpty()) {
                    filteredList.clear()
                } else {
                    moviesFromDB.map {
                        if (it.name!!.contains(searchTerm, ignoreCase = true)) {
                            addContent(it)
                            filteredList.add(it)
                        }
                    }
                }
                val filteredResult = FilterResults()
                filteredResult.values = filteredList
                return filteredResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clearContent()
                if (results?.values != null) {
                    contentList.addAll(results.values as MutableList<Content>)
                    notifyDataSetChanged()
                }
            }
        }
    }

}