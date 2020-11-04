package com.example.mymoviddb.category.movie

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.Error401Model
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class CategoryMovieListAccessTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<Error401Model>
    private lateinit var access: ICategoryMovieListAccess

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = CategoryMovieListAccess(fakeRemoteSource)
        errorConverter = moshi.adapter(Error401Model::class.java)
    }

    @Test
    fun `get popular movie list result success`() = runBlockingTest {
        when (val result = access.getPopularMovieList(1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(MovieModel::class.java))
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get popular movie list result error 401`() = runBlockingTest {
        when (val result = access.getPopularMovieList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.toLowerCase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `get now playing movie list result success`() = runBlockingTest {
        when (val result = access.getNowPlayingMovieList(1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(MovieModel::class.java))
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get now playing movie list result error 401`() = runBlockingTest {
        when (val result = access.getNowPlayingMovieList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.toLowerCase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `search movies result success`() = runBlockingTest {
        when (val result = access.searchMovies("Matrix", 1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(MovieModel::class.java))
                )
                MatcherAssert.assertThat(
                    result.data?.results?.isNotEmpty(),
                    CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `search movies list result success list is empty`() = runBlockingTest {
        when (val result = access.searchMovies("", 1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(MovieModel::class.java))
                )
                MatcherAssert.assertThat(
                    result.data?.results?.isEmpty(),
                    CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `search movies list result error 401`() = runBlockingTest {
        when (val result = access.searchMovies("", 1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.toLowerCase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

}