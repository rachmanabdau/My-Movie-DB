package com.example.mymoviddb.category.account

import androidx.paging.CombinedLoadStates
import com.example.mymoviddb.adapters.CategoryShowAdapter
import com.example.mymoviddb.core.model.ShowResult

interface ResultHandler {

    fun setRetryButton(adapter: CategoryShowAdapter)

    fun navigateToDetailMovie(showItem: ShowResult)

    fun setViewResult(loadState: CombinedLoadStates, favouriteAdapter: CategoryShowAdapter)

    fun showPlaceholderMessage(isItemEmpty: Boolean)
}