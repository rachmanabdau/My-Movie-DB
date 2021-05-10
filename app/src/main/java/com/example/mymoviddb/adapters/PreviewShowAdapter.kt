package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.LoadMoreItemBinding
import com.example.mymoviddb.databinding.MovieItemBinding
import com.example.mymoviddb.model.ShowResult
import com.example.mymoviddb.utils.LoadMoreViewHolder

class PreviewShowAdapter(
    private val action: () -> Unit,
    private val detailAction: (ShowResult) -> Unit,
    private val showLoadMore: Boolean = true
) :
    ListAdapter<ShowResult, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val loadMoreType = 0
    private val movieType = 1

    companion object DiffUtilCallback : DiffUtil.ItemCallback<ShowResult>() {
        override fun areItemsTheSame(
            oldItem: ShowResult,
            newItem: ShowResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShowResult,
            newItem: ShowResult
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 0 && showLoadMore) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            showLoadMore && position == itemCount - 1 -> {
                loadMoreType
            }

            else -> {
                movieType
            }

            //else -> throw IllegalArgumentException("View type not found")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            movieType -> {
                val view =
                    MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PreviewShowViewHolder(view)
            }

            else -> {
                val view =
                    LoadMoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadMoreViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < itemCount - 1) {
            if (holder is PreviewShowViewHolder) {
                val data = getItem(position)
                holder.onBind(data, detailAction)
            }
        } else if (holder is LoadMoreViewHolder) {
            holder.onBind(action)
        }
    }
}

class PreviewShowViewHolder(private val binding: MovieItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ShowResult, detailAction: (ShowResult) -> Unit) {
        binding.popularMovie = data
        binding.rating = (data.voteAverage * 10).toInt()
        binding.cardMovieItem.setOnClickListener {
            detailAction(data)
        }
    }
}
