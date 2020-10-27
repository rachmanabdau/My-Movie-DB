package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.MovieListItemBinding
import com.example.mymoviddb.databinding.TryAgainLoadListBinding
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result

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
        val data = getItem(position)
        if (holder is ShowViewHolder) {
            holder.onBind(data)
        } else {
            val errorMessage = if (state is Result.Error) {
                (state as Result.Error).exception.localizedMessage ?: "Unknown error has occured"
            } else {
                "Unknown error has occured"
            }
            (holder as ErrorViewHolder).onBind(errorMessage) { retry() }
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

class ErrorViewHolder(private val binding: TryAgainLoadListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(errorMessage: String, action: () -> Unit) {
        binding.errorMessage.text = errorMessage
        binding.tryAgainButton.setOnClickListener {
            binding.errorMessage.visibility = View.GONE
            binding.tryAgainButton.visibility = View.GONE
            binding.retryLoading.visibility = View.VISIBLE
            action()
            binding.errorMessage.visibility = View.VISIBLE
            binding.tryAgainButton.visibility = View.VISIBLE
            binding.retryLoading.visibility = View.GONE
        }
    }
}
