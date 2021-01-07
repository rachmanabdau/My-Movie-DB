package com.example.mymoviddb.account.paging

import android.app.Application
import androidx.paging.PagingSource
import com.example.mymoviddb.account.IAccountShowAccess
import com.example.mymoviddb.model.FavouriteAndWatchListShow
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.PreferenceUtil
import com.example.mymoviddb.utils.wrapEspressoIdlingResource

class AccountShowDatasource(
    app: Application,
    private val networkService: IAccountShowAccess,
    private val showType: Int
) : PagingSource<Int, FavouriteAndWatchListShow.Result>() {

    // for sessionId
    private val sessionId: String = PreferenceUtil.readUserSession(app)

    // for user id
    private val userId = PreferenceUtil.readAccountId(app)

    companion object {
        const val FAVOURITE_MOVIES = 31
        const val FAVOURITE_TVSHOWS = 32
        const val WATCHLIST_MOVIES = 41
        const val WATCHLIST_TVSHOWS = 42
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FavouriteAndWatchListShow.Result> {
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
    )
            : Result<FavouriteAndWatchListShow?> {

        return when (showType) {
            FAVOURITE_MOVIES ->
                networkService.getFavouriteMovies(
                    accountId = userId, sessionId = sessionId, nextPageNumber
                )

            FAVOURITE_TVSHOWS ->
                networkService.getFavouriteTVShows(
                    accountId = userId, sessionId = sessionId, nextPageNumber
                )

            WATCHLIST_MOVIES ->
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

    private fun loadResult(result: Result<FavouriteAndWatchListShow?>, nextPageNumber: Int)
            : LoadResult<Int, FavouriteAndWatchListShow.Result> {
        return if (result is Result.Success) {
            val movieList = result.data?.results ?: emptyList()
            LoadResult.Page(
                data = movieList,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } else {
            getErrorMessage(result)
        }
    }

    private fun getErrorMessage(result: Result<*>): LoadResult<Int, FavouriteAndWatchListShow.Result> {
        val errorMessage = if (result is Result.Error) {
            result.exception.localizedMessage ?: "Unknown exception occured."
        } else {
            "Unknown exception occured."
        }

        val exception = Exception(errorMessage)
        return LoadResult.Error(exception)
    }

}