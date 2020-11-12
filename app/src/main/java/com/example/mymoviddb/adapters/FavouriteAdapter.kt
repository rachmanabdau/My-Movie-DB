package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.R
import com.example.mymoviddb.databinding.FavouriteItemBinding
import com.example.mymoviddb.databinding.TryAgainLoadListBinding
import com.example.mymoviddb.model.FavouriteShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.ErrorViewHolder
import timber.log.Timber

class FavouriteAdapter(private val retry: () -> Unit, private val actionDetail: (Long) -> Unit) :
    PagedListAdapter<FavouriteShow.Result, RecyclerView.ViewHolder>(DiffUtil) {

    private val typeError = 0
    private val typeFavouriteShows = 1
    private var state: Result<FavouriteShow?> = Result.Loading


    companion object DiffUtil : ItemCallback<FavouriteShow.Result>() {
        override fun areItemsTheSame(
            oldItem: FavouriteShow.Result,
            newItem: FavouriteShow.Result
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FavouriteShow.Result,
            newItem: FavouriteShow.Result
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() > 1) super.getItemCount() + 1 else super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && state is Result.Error)
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
        if (holder is FavouriteViewHolder && position < itemCount - 2) {
            val data = getItem(position)
            holder.onBind(data, actionDetail)
        } else if (holder is ErrorViewHolder && state is Result.Error) {
            val errorMessage =
                (state as Result.Error).exception.localizedMessage ?: "Unknown error has occured"
            (holder).onBind(state, errorMessage) { retry() }
        }
    }

    fun setState(state: Result<FavouriteShow?>) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

}

class FavouriteViewHolder(private val binding: FavouriteItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: FavouriteShow.Result?, actionDetail: (Long) -> Unit) {
        val context = binding.root.context
        binding.favouriteData = data
        binding.titleFavouriteItem.text = /*data?.tvName ?:*/ data?.movieTitle

        Timber.d(binding.titleFavouriteItem.text.toString())
        binding.showPosterFavouriteItem.contentDescription = context.getString(
            R.string.movie_content_description,
            binding.titleFavouriteItem.text.toString()
        )

        binding.favouriteItemCardContainer.setOnClickListener {
            actionDetail(data?.id ?: 0L)
        }
    }
}