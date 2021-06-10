package com.example.mymoviddb.core

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.mock.FakeDataFactory
import com.example.mymoviddb.core.mock.FakeRemoteServer
import com.example.mymoviddb.core.mock.FakeUserPreference
import com.example.mymoviddb.core.utils.preference.Preference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class ShowDataSourceTest{

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var userPreference: Preference
    private lateinit var fakeDataFactory: FakeDataFactory

    @Before
    fun setupViewModel() {
        userPreference = FakeUserPreference()
        fakeRemoteSource = FakeRemoteServer()
        fakeDataFactory = FakeDataFactory()
    }

    @Test
    fun loadPagedPopularMovies() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.POPULAR_MOVIES)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getPopularMovieResponse().results,
                prevKey = null,
                nextKey = fakeDataFactory.getPopularMovieResponse().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedOnairMovies() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.NOW_PLAYING_MOVIES)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getNowPlayingMovieResponse().results,
                prevKey = null,
                nextKey = fakeDataFactory.getNowPlayingMovieResponse().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedSearchMovies() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.SEARCH_MOVIES, "test")
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getSearchMovieResult().results,
                prevKey = null,
                nextKey = fakeDataFactory.getSearchMovieResult().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedFavouriteMovies() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.FAVOURITE_MOVIES)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getFavouriteMovie().results,
                prevKey = null,
                nextKey = fakeDataFactory.getFavouriteMovie().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedWatchListMovies() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.WATCHLIST_MOVIES)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getWatchListMovie().results,
                prevKey = null,
                nextKey = fakeDataFactory.getWatchListMovie().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedPopularTvShow() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.POPULAR_TV_SHOWS)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getPopularTvShow().results,
                prevKey = null,
                nextKey = fakeDataFactory.getPopularTvShow().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedOnairTvShow() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.ON_AIR_TV_SHOWS)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getOnairTvShow().results,
                prevKey = null,
                nextKey = fakeDataFactory.getOnairTvShow().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedSearchTvShow() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.SEARCH_TV_SHOWS, "test")
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getSearchTvResult().results,
                prevKey = null,
                nextKey = fakeDataFactory.getSearchTvResult().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedFavouriteTvShow() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.FAVOURITE_TV_SHOWS)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getFavouriteTv().results,
                prevKey = null,
                nextKey = fakeDataFactory.getFavouriteTv().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }

    @Test
    fun loadPagedWatchListTvShow() = runBlockingTest {
        val datasourceDependency = DatasourceDependency(userPreference, fakeRemoteSource, ShowCategoryIndex.WATCHLIST_TV_SHOWS)
        val pagingSource = ShowDataSource(datasourceDependency)
        MatcherAssert.assertThat(
            PagingSource.LoadResult.Page(
                data = fakeDataFactory.getWatchListTv().results,
                prevKey = null,
                nextKey = fakeDataFactory.getWatchListTv().page + 1
            ),
            CoreMatchers.`is`(
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = 1,
                        loadSize = 12,
                        placeholdersEnabled = false
                    )
                )
            ),
        )
    }
}