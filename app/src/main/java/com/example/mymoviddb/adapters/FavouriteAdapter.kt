package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.R
import com.example.mymoviddb.databinding.FavouriteItemBinding
import com.example.mymoviddb.model.ShowResult

class FavouriteAdapter(private val actionDetail: (Long) -> Unit) :
    PagingDataAdapter<ShowResult, FavouriteViewHolder>(DiffUtil) {

    companion object DiffUtil : ItemCallback<ShowResult>() {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val data = getItem(position)
        holder.onBind(data, actionDetail)
    }

}

class FavouriteViewHolder(private val binding: FavouriteItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ShowResult?, actionDetail: (Long) -> Unit) {
        data?.let {
            val context = binding.root.context
            binding.favouriteData = data
            binding.titleFavouriteItem.text = data.title
            binding.showPosterFavouriteItem.contentDescription = context.getString(
                R.string.movie_content_description,
                data.title
            )

            binding.favouriteItemCardContainer.setOnClickListener {
                actionDetail(data.id)
            }
        }
    }
}