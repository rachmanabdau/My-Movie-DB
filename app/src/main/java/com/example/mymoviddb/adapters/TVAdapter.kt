package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.TvItemBinding
import com.example.mymoviddb.model.TVShowModel

class TVAdapter(private val action: () -> Unit) :
    ListAdapter<TVShowModel.Result, TVShowViewHolder>(DiffUtilCallback) {

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
        holder.onBind(data, position, itemCount, action)
    }
}

class TVShowViewHolder(private val binding: TvItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: TVShowModel.Result, position: Int, totalItem: Int, action: () -> Unit) {
        binding.popularTv = data
        binding.rating = (data.voteAverage * 10).toInt()

        if (position == totalItem - 1) {
            binding.tvPoster.visibility = View.INVISIBLE
            binding.tvTitleTv.visibility = View.INVISIBLE
            binding.ratingString.visibility = View.INVISIBLE
            binding.loadMore.visibility = View.VISIBLE
            binding.loadMore.setOnClickListener {
                action()
            }
        } else {
            binding.tvPoster.visibility = View.VISIBLE
            binding.tvTitleTv.visibility = View.VISIBLE
            binding.ratingString.visibility = View.VISIBLE
            binding.loadMore.visibility = View.GONE
        }
    }
}