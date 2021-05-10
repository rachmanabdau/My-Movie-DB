package com.example.mymoviddb.category

import android.app.Application
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.ShowResponse
import com.example.mymoviddb.model.ShowResult
import com.example.mymoviddb.utils.PreferenceUtil
import com.example.mymoviddb.utils.wrapEspressoIdlingResource

class ShowDataSource(
    private val app: Application,
    private val networkService: ICategoryShowListAccess,
    private val categoryId: ShowCategoryIndex,
    private val title: String
) : PagingSource<Int, ShowResult>() {

    // for sessionId
    private val sessionId: String = PreferenceUtil.readUserSession(app)

    // for user id
    private val userId = PreferenceUtil.readAccountId(app)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowResult> {
        // Start refresh at page 1 if undefined.
        wrapEspressoIdlingResource {
            val nextPageNumber = params.key ?: 1
            return try {
                val showResult: Result<ShowResponse?> =
                    getShowData(categoryId, nextPageNumber, networkService)


                if (showResult is Result.Success) {
                    val movieList = showResult.data?.results ?: emptyList()
                    LoadResult.Page(
                        data = movieList,
                        prevKey = null,
                        nextKey = nextPageNumber + 1
                    )
                } else {
                    LoadResult.Error(Exception(getErrorMessage(showResult)))
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShowResult>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getErrorMessage(result: Result<*>): String {
        return if (result is Result.Error) {
            result.exception.localizedMessage ?: "Unknown exception occured."
        } else {
            "Unknown exception occured."
        }
    }

    private suspend fun getShowData(
        categoryId: ShowCategoryIndex,
        pageNumber: Int,
        networkService: ICategoryShowListAccess
    ): Result<ShowResponse?> {
        return when {
            categoryId == ShowCategoryIndex.SEARCH_MOVIES && title.isNotBlank() -> {
                networkService.searchMovies(title, pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ShowCategoryIndex.POPULAR_MOVIES -> {
                networkService.getPopularMovieList(pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ShowCategoryIndex.NOW_PLAYING_MOVIES -> {
                networkService.getNowPlayingMovieList(pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ShowCategoryIndex.FAVOURITE_MOVIES ->
                networkService.getFavouriteMovies(
                    accountId = userId, sessionId = sessionId, pageNumber
                )
            categoryId == ShowCategoryIndex.WATCHLIST_MOVIES ->
                networkService.getWatchlistMovies(
                    accountId = userId, sessionId = sessionId, pageNumber
                )
            categoryId == ShowCategoryIndex.SEARCH_TV_SHOWS && title.isNotBlank() -> {
                networkService.searchTvShowList(title, pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ShowCategoryIndex.POPULAR_TV_SHOWS -> {
                networkService.getPopularTvShowList(pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ShowCategoryIndex.ON_AIR_TV_SHOWS -> {
                networkService.getOnAirTvShowList(pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ShowCategoryIndex.FAVOURITE_TV_SHOWS ->
                networkService.getFavouriteTVShows(
                    accountId = userId, sessionId = sessionId, pageNumber
                )
            categoryId == ShowCategoryIndex.WATCHLIST_TV_SHOWS -> networkService.getWatchlistTVShows(
                accountId = userId, sessionId = sessionId, pageNumber
            )
            else -> {
                Result.Success(null)
            }
        }
    }
}