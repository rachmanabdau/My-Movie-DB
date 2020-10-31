package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.LoadMoreItemBinding
import com.example.mymoviddb.databinding.TvItemBinding
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.LoadMoreViewHolder

class TVAdapter(private val action: () -> Unit) :
    ListAdapter<TVShowModel.Result, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val loadMoreType = 0
    private val tvShowType = 1

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

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 1) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < itemCount - 1) tvShowType else loadMoreType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == tvShowType) {
            val view = TvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TVShowViewHolder(view)
        } else {
            val view =
                LoadMoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            LoadMoreViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TVShowViewHolder && position < itemCount - 1) {
            val data = getItem(position)
            holder.onBind(data)
        } else {
            (holder as LoadMoreViewHolder).onBind { action() }
        }
    }
}

class TVShowViewHolder(private val binding: TvItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: TVShowModel.Result/*, action: () -> Unit*/) {
        binding.popularTv = data
        binding.rating = (data.voteAverage * 10).toInt()
    }
}