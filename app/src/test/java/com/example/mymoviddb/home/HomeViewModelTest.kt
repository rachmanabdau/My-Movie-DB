package com.example.mymoviddb.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymoviddb.datasource.remote.FakeRemoteServer
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.getOrAwaitValue
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: RemoteServer
    private lateinit var homeViewModel: HomeViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = FakeRemoteServer()
        homeViewModel = HomeViewModel(ApplicationProvider.getApplicationContext(), fakeRemoteSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    /**
     * Get popular movie list with valid apikey
     * result valie should not be null
     */
    @Test
    fun getPopularMovieList_withValidApiToken_resultNotNull() = runBlocking {
        // WHEN User requesting a popular movie list
        homeViewModel.getPopularMovieList()

        // THEN Response from server should not be null
        when (val result = homeViewModel.popularMovieList.getOrAwaitValue()) {
            is Result.Success -> assertThat(result.data, notNullValue())
            is Result.Loading -> {/* Do nothingm just wait the result either success or failed */
            }
            is Result.Error -> Assert.fail(
                "Testing get popular movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
        }

    }

    /**
     * Get popular movie list with valid apikey
     * result valie should not be null
     */
    @Test
    fun getPopularMovieList_withInvaalidApiToken_resultError() = runBlocking {
        // WHEN User requesting a popular movie list
        homeViewModel.getPopularMovieList(1, "invalid key")

        // THEN Response from server should not be null
        when (val result = homeViewModel.popularMovieList.getOrAwaitValue()) {
            is Result.Success -> Assert.fail(
                "Testing get popular movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
            is Result.Loading -> {/* Do nothingm just wait the result either success or failed */
            }
            is Result.Error -> {
                Assert.assertThat(
                    result.exception.toString().contains("invalid"), `is`(true)
                )
            }

        }
    }

    /**
     * Get popular movie list with valid apikey
     * result valie should not be null
     */
    @Test
    fun getUpcomingMovieList_withValidApiToken_resultNotNull() = runBlocking {
        // WHEN User requesting a popular movie list
        homeViewModel.getBowPlayingMovieList()

        // THEN Response from server should not be null
        when (val result = homeViewModel.nowPlayingMovieList.getOrAwaitValue()) {
            is Result.Success -> assertThat(result.data, notNullValue())
            is Result.Loading -> {/* Do nothingm just wait the result either success or failed */
            }
            is Result.Error -> Assert.fail(
                "Testing get popular movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
        }

    }

    /**
     * Get popular movie list with valid apikey
     * result valie should not be null
     */
    @Test
    fun getUpcominfMovies_withInvalidApiToken_resultError() = runBlocking {
        // WHEN User requesting a popular movie list
        homeViewModel.getBowPlayingMovieList(1, "invalid key")

        // THEN Response from server should not be null
        when (val result = homeViewModel.nowPlayingMovieList.getOrAwaitValue()) {
            is Result.Success -> Assert.fail(
                "Testing get upcoming movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
            is Result.Loading -> {/* Do nothingm just wait the result either success or failed */
            }
            is Result.Error -> {
                Assert.assertThat(
                    result.exception.toString().contains("invalid"), `is`(true)
                )
            }
        }
    }
}