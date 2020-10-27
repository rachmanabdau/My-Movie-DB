package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.MovieItemBinding
import com.example.mymoviddb.model.MovieModel

class MoviesAdapter(private val action: () -> Unit) :
    ListAdapter<MovieModel.Result, MovieViewHolder>(DiffUtilCallback) {

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
        val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data, position, itemCount, action)
    }
}

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: MovieModel.Result, position: Int, totalItem: Int, action: () -> Unit) {
        binding.popularMovie = data
        binding.rating = (data.voteAverage * 10).toInt()

        if (position == totalItem - 1) {
            binding.moviePoster.visibility = View.INVISIBLE
            binding.movieTitleTv.visibility = View.INVISIBLE
            binding.rateingString.visibility = View.INVISIBLE
            binding.loadMore.visibility = View.VISIBLE
            binding.loadMore.setOnClickListener {
                action()
            }
        } else {
            binding.moviePoster.visibility = View.VISIBLE
            binding.movieTitleTv.visibility = View.VISIBLE
            binding.rateingString.visibility = View.VISIBLE
            binding.loadMore.visibility = View.GONE
        }
    }
}
