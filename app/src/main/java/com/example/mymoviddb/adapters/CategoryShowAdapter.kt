package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.MovieListItemBinding
import com.example.mymoviddb.model.ShowResult

class CategoryShowAdapter(private val actionDetail: (ShowResult) -> Unit) :
    PagingDataAdapter<ShowResult, CategoryShowViewHolder>(DiffUtilCallback) {

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

    override fun onBindViewHolder(holder: CategoryShowViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data, actionDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryShowViewHolder {
        val view = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryShowViewHolder(view)
    }
}

class CategoryShowViewHolder(private val binding: MovieListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ShowResult?, actionDetail: (ShowResult) -> Unit) {
        if (data != null) {
            binding.show = data
            binding.rating = (data.voteAverage * 10).toInt()
            binding.cardMovieListItem.setOnClickListener {
                actionDetail(data)
            }
        }
    }
}