package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.TvItemBinding
import com.example.mymoviddb.model.TVShowModel

class TVAdapter : ListAdapter<TVShowModel.Result, TVShowViewHolder>(DiffUtilCallback) {

    companion object DiffUtilCallback : DiffUtil.ItemCallback<TVShowModel.Result>() {
        override fun areItemsTheSame(
            oldItem: TVShowModel.Result,
            newItem: TVShowModel.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TVShowModel.Result,
            newItem: TVShowModel.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val view = TvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data)
    }
}

class TVShowViewHolder(private val binding: TvItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: TVShowModel.Result) {
        binding.popularTv = data
        binding.rating = (data.voteAverage * 10).toInt()
    }
}