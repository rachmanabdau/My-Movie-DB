package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.LoadMoreItemBinding
import com.example.mymoviddb.databinding.TvItemBinding
import com.example.mymoviddb.model.PreviewTvShow
import com.example.mymoviddb.utils.LoadMoreViewHolder

class TVAdapter(
    private val action: () -> Unit,
    private val detailAction: (Long) -> Unit,
    private val showLoadMore: Boolean = true
) :
    ListAdapter<PreviewTvShow.Result, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val loadMoreType = 0
    private val tvShowType = 1

    companion object DiffUtilCallback : DiffUtil.ItemCallback<PreviewTvShow.Result>() {
        override fun areItemsTheSame(
            oldItem: PreviewTvShow.Result,
            newItem: PreviewTvShow.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PreviewTvShow.Result,
            newItem: PreviewTvShow.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 1 && showLoadMore) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && showLoadMore) loadMoreType else tvShowType
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
            holder.onBind(data, detailAction)
        } else if (holder is LoadMoreViewHolder) {
            (holder).onBind { action() }
        }
    }
}

class TVShowViewHolder(private val binding: TvItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: PreviewTvShow.Result, detailAction: (Long) -> Unit) {
        binding.popularTv = data
        binding.rating = (data.voteAverage * 10).toInt()
        binding.tvCardItem.setOnClickListener {
            detailAction(data.id)
        }

    }
}