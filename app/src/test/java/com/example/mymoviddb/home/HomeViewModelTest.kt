package com.example.mymoviddb.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymoviddb.core.FakeRemoteServer
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.getOrAwaitValue
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: IHomeAccess
    private lateinit var homeViewModel: HomeViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = HomeRepository(FakeRemoteServer())
        homeViewModel = HomeViewModel(fakeRemoteSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    /**
     * Get popular movie list with valid apikey
     * result value should not be null
     */
    @Test
    fun getPopularMovieList_withValidApiToken_resultNotNull() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getPopularMovieList()

        // THEN Response from server should not be null
        when (val result = homeViewModel.popularMovieList.getOrAwaitValue()) {
            is Result.Success -> assertThat(result.data, notNullValue())
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> fail(
                "Testing get popular movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
        }

    }

    /**
     * Get popular movie list with invalid apikey
     * result value should not be null
     */
    @Test
    fun getPopularMovieList_withInvalidApiToken_resultError() = runBlockingTest {
        delay(3000)
        // WHEN User requesting a popular movie list
        homeViewModel.getPopularMovieList(1, "invalid key")
        val result = homeViewModel.popularMovieList.getOrAwaitValue()
        println(result.toString())
        // THEN Response from server should not be null
        when (result) {
            is Result.Success -> {
                fail(
                    "Testing get popular movie is failed. Either Object ist not " +
                            "Result.Success or result equals to null"
                )
            }
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> {
                assertThat(
                    result.exception.toString().contains("Invalid"), `is`(true)
                )
            }

        }
    }

    /**
     * Get Now Playing movie list with valid apikey
     * result value should not be null
     */
    @Test
    fun getNowPlayingMovieList_withValidApiToken_resultNotNull() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getNowPlayingMovieList()

        // THEN Response from server should not be null
        when (val result = homeViewModel.nowPlayingMovieList.getOrAwaitValue()) {
            is Result.Success -> assertThat(result.data, notNullValue())
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> fail(
                "Testing get Now g movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
        }

    }

    /**
     * Get Now Playing movie list with valid apikey
     * result value should not be null
     */
    @Test
    fun getNowPlayingMovies_withInvalidApiToken_resultError() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getNowPlayingMovieList(1, "invalid key")

        // THEN Response from server should not be null
        when (val result = homeViewModel.nowPlayingMovieList.getOrAwaitValue()) {
            is Result.Success -> fail(
                "Testing get Now Playing movie is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> {
                assertThat(
                    result.exception.toString().contains("Invalid"), `is`(true)
                )
            }
        }
    }

    /**
     * Get Popular Tv Show list with valid apikey
     * result value should not be null
     */
    @Test
    fun getPopularTvList_withValidApiToken_resultNotNull() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getPopularTVList()

        // THEN Response from server should not be null
        when (val result = homeViewModel.popularTVList.getOrAwaitValue()) {
            is Result.Success -> assertThat(result.data, notNullValue())
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> fail(
                "Testing get Popular Tv Show is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
        }

    }

    /**
     * Get Popular Tv Show list with valid apikey
     * result value should not be null
     * this test should be run individually
     */
    @Test
    fun getPopularTvMovies_withInvalidApiToken_resultError() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getNowPlayingMovieList(1, "invalid key")

        // THEN Response from server should not be null
        when (val result = homeViewModel.nowPlayingMovieList.getOrAwaitValue()) {
            is Result.Success -> fail(
                "Testing get Popular Tv Show is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> {
                assertThat(
                    result.exception.toString().contains("Invalid"), `is`(true)
                )
            }
        }
    }

    /**
     * Get Popular Tv Show list with valid apikey
     * result value should not be null
     */
    @Test
    fun getOnAirTvList_withValidApiToken_resultNotNull() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getonAirTVList()

        // THEN Response from server should not be null
        when (val result = homeViewModel.onAirTVList.getOrAwaitValue()) {
            is Result.Success -> assertThat(result.data, notNullValue())
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> fail(
                "Testing get Popular Tv Show is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
        }

    }

    /**
     * Get Popular Tv Show list with valid apikey
     * result value should not be null
     * this test should be run individually
     */
    @Test
    fun getOnAirTvMovies_withInvalidApiToken_resultError() = runBlockingTest {
        // WHEN User requesting a popular movie list
        homeViewModel.getonAirTVList(1, "invalid key")

        // THEN Response from server should not be null
        when (val result = homeViewModel.onAirTVList.getOrAwaitValue()) {
            is Result.Success -> fail(
                "Testing get Popular Tv Show is failed. Either Object ist not " +
                        "Result.Success or result equals to null"
            )
            is Result.Loading -> {/* Do nothing just wait the result either success or failed */
            }
            is Result.Error -> {
                assertThat(
                    result.exception.toString().contains("Invalid"), `is`(true)
                )
            }
        }
    }
}