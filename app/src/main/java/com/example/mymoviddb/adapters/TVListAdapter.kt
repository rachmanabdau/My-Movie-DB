package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviddb.databinding.TryAgainLoadListBinding
import com.example.mymoviddb.databinding.TvListItemBinding
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.ErrorViewHolder

class TVListAdapter(private val retry: () -> Unit, private val actionDetail: (Long) -> Unit) :
    PagedListAdapter<TVShowModel.Result, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private val dataViewType = 1
    private val errorViewType = 2
    private var state: Result<TVShowModel?> = Result.Loading

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
        return if (super.getItemCount() > 19) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && (state is Result.Error || state is Result.Loading))
            errorViewType
        else dataViewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == dataViewType) {
            val view =
                TvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TVListViewHolder(view)
        } else {
            val view =
                TryAgainLoadListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ErrorViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TVListViewHolder) {
            if (itemCount > 20 && position < itemCount - 1) {
                val data = getItem(position)
                holder.onBind(data, actionDetail)
            } else if (itemCount < 20 && position < itemCount) {
                val data = getItem(position)
                holder.onBind(data, actionDetail)
            }
        } else if (holder is ErrorViewHolder) {
            val errorMessage = if (state is Result.Error) {
                (state as Result.Error).exception.localizedMessage ?: "Unknown error has occured"
            } else {
                "Unknown error has occured"
            }

            val staggaredLayoutParam =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            staggaredLayoutParam.isFullSpan = true
            (holder).onBind(state, errorMessage) { retry() }
        }
    }

    fun setState(state: Result<TVShowModel?>) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }
}


class TVListViewHolder(private val binding: TvListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: TVShowModel.Result?, actionDetail: (Long) -> Unit) {
        if (data != null) {
            binding.show = data
            binding.rating = (data.voteAverage * 10).toInt()

            binding.cardTvListItem.setOnClickListener {
                actionDetail(data.id)
            }
        }
    }
}