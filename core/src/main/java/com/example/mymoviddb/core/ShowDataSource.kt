package com.example.mymoviddb.core

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.ShowResponse
import com.example.mymoviddb.core.model.ShowResult
import com.example.mymoviddb.core.utils.wrapEspressoIdlingResource

class ShowDataSource(
    private val sourceDependency: DatasourceDependency
) : PagingSource<Int, ShowResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowResult> {
        // Start refresh at page 1 if undefined.
        wrapEspressoIdlingResource {
            val nextPageNumber = params.key ?: 1
            val showType = sourceDependency.showType
            return try {
                val showResult: Result<ShowResponse?> =
                    getShowData(showType, nextPageNumber)


                if (showResult is Result.Success) {
                    val showList = showResult.data?.results ?: emptyList()
                    val nextKey = if (showList.isEmpty()) null else nextPageNumber + 1
                    LoadResult.Page(
                        data = showList,
                        prevKey = null,
                        nextKey = nextKey
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
        showType: ShowCategoryIndex,
        pageNumber: Int
    ): Result<ShowResponse?> {
        return when (showType) {
            ShowCategoryIndex.SEARCH_MOVIES ->
                sourceDependency.searchMovie(pageNumber)

            ShowCategoryIndex.POPULAR_MOVIES ->
                sourceDependency.getPopularMovies(pageNumber)

            ShowCategoryIndex.NOW_PLAYING_MOVIES ->
                sourceDependency.getNowPlayiingMovies(pageNumber)

            ShowCategoryIndex.FAVOURITE_MOVIES ->
                sourceDependency.getFavouriteMovies(pageNumber)

            ShowCategoryIndex.WATCHLIST_MOVIES ->
                sourceDependency.getWatchListMovies(pageNumber)

            ShowCategoryIndex.SEARCH_TV_SHOWS ->
                sourceDependency.searchTvShows(pageNumber)

            ShowCategoryIndex.POPULAR_TV_SHOWS ->
                sourceDependency.getPopularTvShows(pageNumber)

            ShowCategoryIndex.ON_AIR_TV_SHOWS ->
                sourceDependency.getOnAirTvShows(pageNumber)

            ShowCategoryIndex.FAVOURITE_TV_SHOWS ->
                sourceDependency.getFavouriteTvShows(pageNumber)

            ShowCategoryIndex.WATCHLIST_TV_SHOWS ->
                sourceDependency.getWatchListTvShows(pageNumber)
        }
    }
}