package com.example.mymoviddb.account.paging

import android.app.Application
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymoviddb.account.IAccountShowAccess
import com.example.mymoviddb.category.AccountShowCategoryIndex
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.ShowResponse
import com.example.mymoviddb.model.ShowResult
import com.example.mymoviddb.utils.PreferenceUtil
import com.example.mymoviddb.utils.wrapEspressoIdlingResource

class AccountShowDatasource(
    app: Application,
    private val networkService: IAccountShowAccess,
    private val showType: AccountShowCategoryIndex
) : PagingSource<Int, ShowResult>() {

    // for sessionId
    private val sessionId: String = PreferenceUtil.readUserSession(app)

    // for user id
    private val userId = PreferenceUtil.readAccountId(app)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowResult> {
        wrapEspressoIdlingResource {
            val nextPageNumber = params.key ?: 1
            return try {
                val showResult = getShowList(networkService, nextPageNumber)
                loadResult(showResult, nextPageNumber)
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    private suspend fun getShowList(
        networkService: IAccountShowAccess,
        nextPageNumber: Int
    ): Result<ShowResponse?> {
        return when (showType) {
            AccountShowCategoryIndex.FAVOURITE_MOVIES ->
                networkService.getFavouriteMovies(
                    accountId = userId, sessionId = sessionId, nextPageNumber
                )

            AccountShowCategoryIndex.FAVOURITE_TV_SHOWS ->
                networkService.getFavouriteTVShows(
                    accountId = userId, sessionId = sessionId, nextPageNumber
                )

            AccountShowCategoryIndex.WATCHLIST_MOVIES ->
                networkService.getWatchlistMovies(
                    accountId = userId,
                    sessionId = sessionId,
                    nextPageNumber
                )

            // WATCHLIST_TVSHOWS
            else -> networkService.getWatchlistTVShows(
                accountId = userId,
                sessionId = sessionId,
                nextPageNumber
            )
        }
    }

    private fun loadResult(result: Result<ShowResponse?>, nextPageNumber: Int)
            : LoadResult<Int, ShowResult> {
        return if (result is Result.Success) {
            val accountShowList: List<ShowResult> = result.data?.results ?: emptyList()
            LoadResult.Page(
                data = accountShowList,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } else {
            getErrorMessage(result)
        }
    }

    private fun getErrorMessage(result: Result<*>): LoadResult<Int, ShowResult> {
        val errorMessage = if (result is Result.Error) {
            result.exception.localizedMessage ?: "Unknown exception occured."
        } else {
            "Unknown exception occured."
        }

        val exception = Exception(errorMessage)
        return LoadResult.Error(exception)
    }

    override fun getRefreshKey(state: PagingState<Int, ShowResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}