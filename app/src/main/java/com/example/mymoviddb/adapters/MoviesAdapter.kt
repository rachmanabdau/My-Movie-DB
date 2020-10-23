package com.example.mymoviddb.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.MovieItemBinding
import com.example.mymoviddb.model.MovieModel

class MoviesAdapter : ListAdapter<MovieModel.Result, MovieViewHolder>(DiffUtilCallback) {

    companion object DiffUtilCallback : DiffUtil.ItemCallback<MovieModel.Result>() {
        override fun areItemsTheSame(
            oldItem: MovieModel.Result,
            newItem: MovieModel.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieModel.Result,
            newItem: MovieModel.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieItemBinding.bind(parent.rootView)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data)
    }
}

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: MovieModel.Result) {
        binding.popularMovie = data
    }
}
