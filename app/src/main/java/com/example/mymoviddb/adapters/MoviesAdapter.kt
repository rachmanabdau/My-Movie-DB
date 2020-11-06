package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.LoadMoreItemBinding
import com.example.mymoviddb.databinding.MovieItemBinding
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.utils.LoadMoreViewHolder

class MoviesAdapter(
    private val action: () -> Unit,
    private val detailAction: (Long) -> Unit,
    private val showLoadMore: Boolean = true
) :
    ListAdapter<MovieModel.Result, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val loadMoreType = 0
    private val movieType = 1

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

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 0 && showLoadMore) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && showLoadMore) loadMoreType else movieType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == movieType) {
            val view = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MovieViewHolder(view)
        } else {
            val view =
                LoadMoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadMoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder && position < itemCount - 1) {
            val data = getItem(position)
            holder.onBind(data, detailAction)
        } else if (holder is LoadMoreViewHolder) {
            holder.onBind(action)
        }
    }
}

class MovieViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: MovieModel.Result, detailAction: (Long) -> Unit) {
        binding.popularMovie = data
        binding.rating = (data.voteAverage * 10).toInt()
        binding.cardMovieItem.setOnClickListener {
            detailAction(data.id)
        }
    }
}
