package com.example.mymoviddb.category.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymoviddb.category.CategoryShowListRepository
import com.example.mymoviddb.category.ICategoryShowListAccess
import com.example.mymoviddb.category.ShowCategoryIndex
import com.example.mymoviddb.getOrAwaitValue
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.example.mymoviddb.sharedData.FakeUserPreference
import com.example.mymoviddb.utils.preference.Preference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AccountShowViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRepository: ICategoryShowListAccess
    private lateinit var fakeUserPreference: Preference
    private lateinit var accountShowViewModel: AccountShowViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeUserPreference = FakeUserPreference()
        fakeRepository = CategoryShowListRepository(FakeRemoteServer())
        accountShowViewModel = AccountShowViewModel(fakeUserPreference, fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getPopularMovies_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.POPULAR_MOVIES)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getNowPlayingMovies_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.NOW_PLAYING_MOVIES)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getFavouriteMovies_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.FAVOURITE_MOVIES)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getWatchListMovies_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.WATCHLIST_MOVIES)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getPopularTvShows_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.POPULAR_TV_SHOWS)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getOnAirTvShows_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.ON_AIR_TV_SHOWS)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getFavouriteTvShows_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.FAVOURITE_TV_SHOWS)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun getWatchListShows_resultSuccess() = runBlockingTest {
        accountShowViewModel.getShowList(ShowCategoryIndex.WATCHLIST_TV_SHOWS)
        val result = accountShowViewModel.accountShowList.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }
}