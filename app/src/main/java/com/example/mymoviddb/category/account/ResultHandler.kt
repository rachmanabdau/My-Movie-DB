package com.example.mymoviddb.category.account

import androidx.paging.CombinedLoadStates
import com.example.mymoviddb.adapters.CategoryShowAdapter

interface ResultHandler {

    fun setRetryButton(adapter: CategoryShowAdapter)

    fun navigateToDetailMovie(movieId: Long)

    fun setViewResult(loadState: CombinedLoadStates, favouriteAdapter: CategoryShowAdapter)

    fun showPlaceholderMessage(isItemEmpty: Boolean)
}