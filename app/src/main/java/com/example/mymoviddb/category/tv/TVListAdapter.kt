package com.example.mymoviddb.category.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.mymoviddb.adapters.TVListViewHolder
import com.example.mymoviddb.databinding.TvListItemBinding
import com.example.mymoviddb.model.TVShowModel

class TVListAdapterV3(private val actionDetail: (Long) -> Unit) :
    PagingDataAdapter<TVShowModel.Result, TVListViewHolder>(DiffUtilCallback) {

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

    override fun onBindViewHolder(holder: TVListViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data, actionDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVListViewHolder {
        val view =
            TvListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVListViewHolder(view)
    }
}