package com.example.mymoviddb.feature.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymoviddb.core.mock.FakeRemoteServer
import com.example.mymoviddb.core.mock.FakeUserPreference
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.feature.category.CategoryShowListRepository
import com.example.mymoviddb.feature.category.ICategoryShowListAccess
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
class SearchViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRepository: ICategoryShowListAccess
    private lateinit var fakeUserPreference: Preference
    private lateinit var searchViewmodel: SearchViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeUserPreference = FakeUserPreference()
        fakeRepository = CategoryShowListRepository(FakeRemoteServer())
        searchViewmodel =
            SearchViewModel(fakeUserPreference, fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun searchMovies_resultSuccess() = runBlockingTest {
        searchViewmodel.searchShow(ShowCategoryIndex.SEARCH_MOVIES, "The Matrix")
        val result = searchViewmodel.showPageData.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())
    }

    @Test
    fun searchTvShows_resultSuccess() = runBlockingTest {
        searchViewmodel.searchShow(ShowCategoryIndex.SEARCH_TV_SHOWS, "The Simpsons")
        val result = searchViewmodel.showPageData.getOrAwaitValue()
        MatcherAssert.assertThat(result, CoreMatchers.notNullValue())

    }
}