package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.R
import com.example.mymoviddb.databinding.FavouriteItemBinding
import com.example.mymoviddb.databinding.TryAgainLoadListBinding
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.ErrorViewHolder

class FavouriteAdapter(private val retry: () -> Unit, private val actionDetail: (Long) -> Unit) :
    PagedListAdapter<FavouriteAndWatchListShow.Result, RecyclerView.ViewHolder>(DiffUtil) {

    private val typeError = 0
    private val typeFavouriteShows = 1
    private var state: Result<FavouriteAndWatchListShow?> = Result.Loading


    companion object DiffUtil : ItemCallback<FavouriteAndWatchListShow.Result>() {
        override fun areItemsTheSame(
            oldItem: FavouriteAndWatchListShow.Result,
            newItem: FavouriteAndWatchListShow.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavouriteAndWatchListShow.Result,
            newItem: FavouriteAndWatchListShow.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && state !is Result.Success)
            typeError
        else typeFavouriteShows
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            typeFavouriteShows -> {
                val view =
                    FavouriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FavouriteViewHolder(view)
            }
            else -> {
                val view =
                    TryAgainLoadListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ErrorViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FavouriteViewHolder) {
            val data = getItem(position)
            holder.onBind(data, actionDetail)
        } else if (holder is ErrorViewHolder && state !is Result.Success) {
            val errorMessage =
                if (state is Result.Error) (state as Result.Error).exception.localizedMessage else "Unknown error has occured"
            holder.onBind(state, errorMessage) { retry() }
        }
    }

    fun setState(state: Result<FavouriteAndWatchListShow?>) {
        this.state = state
        notifyDataSetChanged()
    }

}

class FavouriteViewHolder(private val binding: FavouriteItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: FavouriteAndWatchListShow.Result?, actionDetail: (Long) -> Unit) {
        data?.let {
            val context = binding.root.context
            binding.favouriteData = data
            binding.titleFavouriteItem.text = it.tvName ?: it.movieTitle

            binding.showPosterFavouriteItem.contentDescription = context.getString(
                R.string.movie_content_description,
                binding.titleFavouriteItem.text.toString()
            )

            binding.favouriteItemCardContainer.setOnClickListener {
                actionDetail(data.id)
            }
        }
    }
}