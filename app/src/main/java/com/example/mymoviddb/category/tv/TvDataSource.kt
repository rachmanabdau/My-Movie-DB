package com.example.mymoviddb.category.tv

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.utils.wrapEspressoIdlingResource

class TVDataSource(
    private val networkService: ICategoryTVListAccess,
    private val categoryId: Int,
    private val title: String
) : PagingSource<Int, TVShowModel.Result>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TVShowModel.Result> {
        wrapEspressoIdlingResource {
            return try {
                val pageNumber = params.key ?: 1
                val tvShowResult = tvShowData(categoryId, pageNumber, networkService)

                if (tvShowResult is Result.Success) {
                    val tvShowList = tvShowResult.data?.results ?: emptyList()
                    LoadResult.Page(data = tvShowList, prevKey = null, nextKey = pageNumber + 1)
                } else {
                    LoadResult.Error(getErrorMessage(tvShowResult))
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TVShowModel.Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        const val POPULAR_TV_ID = 1
        const val ON_AIR_TV_ID = 2
        const val SEARCH_TV = 32
    }


    private fun getErrorMessage(result: Result<*>): Exception {
        return if (result is Result.Error) {
            Exception(result.exception.localizedMessage ?: "Unknown exception occured.")
        } else {
            Exception("Unknown exception occured.")
        }
    }

    private suspend fun tvShowData(
        categoryId: Int,
        pageNumber: Int,
        networkService: ICategoryTVListAccess
    ): Result<TVShowModel?> {
        return when {
            categoryId == SEARCH_TV && title.isNotBlank() -> {
                networkService.searchTvShowList(title, pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == POPULAR_TV_ID -> {
                networkService.getPopularTvShowList(pageNumber, BuildConfig.V3_AUTH)
            }
            categoryId == ON_AIR_TV_ID -> {
                networkService.getOnAirTvShowList(pageNumber, BuildConfig.V3_AUTH)
            }
            else -> {
                Result.Success(null)
            }
        }
    }
}