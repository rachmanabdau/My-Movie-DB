package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.MovieListItemBinding
import com.example.mymoviddb.model.MovieModel

class MovieListAdapterV3(private val actionDetail: (Long) -> Unit) :
    PagingDataAdapter<MovieModel.Result, ShowViewHolder>(DiffUtilCallback) {

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

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data, actionDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShowViewHolder(view)
    }
}

class ShowViewHolder(private val binding: MovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: MovieModel.Result?, actionDetail: (Long) -> Unit) {
        if (data != null) {
            binding.show = data
            binding.rating = (data.voteAverage * 10).toInt()
            binding.cardMovieListItem.setOnClickListener {
                actionDetail(data.id)
            }
        }
    }
}