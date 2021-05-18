package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.core.R
import com.example.mymoviddb.core.databinding.FavouriteItemBinding
import com.example.mymoviddb.core.databinding.MovieListItemBinding
import com.example.mymoviddb.core.model.ShowResult
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@WithFragmentBindings
class CategoryShowAdapter @Inject constructor() :
    PagingDataAdapter<ShowResult, RecyclerView.ViewHolder>(DiffUtilCallback) {

    private var isUserAuthenticationNeeded = true
    private var _actionDetail: (ShowResult) -> Unit = {}

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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        if (holder is CategoryShowViewHolder) {
            holder.onBind(data, _actionDetail)
        } else {
            (holder as FavouriteViewHolder).onBind(data, _actionDetail)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isUserAuthenticationNeeded) {
            val view =
                FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FavouriteViewHolder(view)
        } else {
            val view =
                MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CategoryShowViewHolder(view)
        }
    }

    fun setItemClick(itemClick: (ShowResult) -> Unit) {
        _actionDetail = itemClick
    }

    fun doesNotNeedAuthentication() {
        isUserAuthenticationNeeded = false
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

class FavouriteViewHolder(private val binding: FavouriteItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ShowResult?, actionDetail: (ShowResult) -> Unit) {
        data?.let {
            val context = binding.root.context
            binding.favouriteData = data
            binding.titleFavouriteItem.text = data.title
            binding.showPosterFavouriteItem.contentDescription = context.getString(
                R.string.movie_content_description,
                data.title
            )

            binding.favouriteItemCardContainer.setOnClickListener {
                actionDetail(data)
            }
        }
    }
}