package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviddb.databinding.MovieListItemBinding
import com.example.mymoviddb.databinding.TryAgainLoadListBinding
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.ErrorViewHolder

class MovieListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<MovieModel.Result, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val dataViewType = 1
    private val errorViewType = 2
    private var state: Result<MovieModel?> = Result.Loading

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
        return if (super.getItemCount() > 1) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && (state is Result.Error || state is Result.Loading))
            errorViewType
        else dataViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == dataViewType) {
            val view =
                MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ShowViewHolder(view)
        } else {
            val view =
                TryAgainLoadListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ErrorViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ShowViewHolder && position < itemCount - 2) {
            val data = getItem(position)
            holder.onBind(data)
        } else if (holder is ErrorViewHolder) {
            val errorMessage = if (state is Result.Error) {
                (state as Result.Error).exception.localizedMessage ?: "Unknown error has occured"
            } else {
                "Unknown error has occured"
            }
            val staggaredLayoutParam =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            staggaredLayoutParam.isFullSpan = true

            (holder).onBind(errorMessage) { retry() }
        }
    }

    fun setState(state: Result<MovieModel?>) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}

class ShowViewHolder(private val binding: MovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: MovieModel.Result?) {
        if (data != null) {
            binding.show = data
            binding.rating = (data.voteAverage * 10).toInt()
        }
    }
}
