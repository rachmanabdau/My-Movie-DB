package com.example.mymoviddb.account

import androidx.paging.CombinedLoadStates
import com.example.mymoviddb.adapters.FavouriteAdapter

interface ResultHandler {

    fun setRetryButton(adapter: FavouriteAdapter)

    fun navigateToDetailMovie(movieId: Long)

    fun setViewResult(loadState: CombinedLoadStates, favouriteAdapter: FavouriteAdapter)

    fun showPlaceholderMessage(isItemEmpty: Boolean)
}