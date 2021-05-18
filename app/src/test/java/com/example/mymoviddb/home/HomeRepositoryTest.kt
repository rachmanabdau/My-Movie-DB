package com.example.mymoviddb.home

import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.remote.NetworkService
import com.example.mymoviddb.core.remote.moshi
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class HomeRepositoryTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<ResponsedBackend>
    private lateinit var access: IHomeAccess

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = HomeRepository(fakeRemoteSource)
        errorConverter = moshi.adapter(ResponsedBackend::class.java)
    }

    @Test
    fun `get popular movie list with valid api key result success`() = runBlockingTest {
        when (val result = access.getPopularMovieList(1)) {
            is Result.Success -> {
                assertThat(result.data, `is`(notNullValue(PreviewMovie::class.java)))
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get popular movie list with invalid api key result error 401`() = runBlockingTest {
        when (val result = access.getPopularMovieList(1, "randomInvalidKey")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    `is`(containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `get now playing movie list with valid api key result success`() = runBlockingTest {
        when (val result = access.getNowPlayingMovieList(1)) {
            is Result.Success -> {
                assertThat(result.data, `is`(notNullValue(PreviewMovie::class.java)))
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get now playing movie list with invalid api key result error 401`() = runBlockingTest {
        when (val result = access.getNowPlayingMovieList(1, "randomInvalidKey")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    `is`(containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `get popular tv show list with valid api key result success`() = runBlockingTest {
        when (val result = access.getPopularTvShowList(1)) {
            is Result.Success -> {
                assertThat(result.data, `is`(notNullValue(PreviewTvShow::class.java)))
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get popular tv show list with invalid api key result error 401`() = runBlockingTest {
        when (val result = access.getPopularTvShowList(1, "randomInvalidKey")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    `is`(containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `get on air tv show list with valid api key result success`() = runBlockingTest {
        when (val result = access.getOnAirTvShowList(1)) {
            is Result.Success -> {
                assertThat(result.data, `is`(notNullValue(PreviewTvShow::class.java)))
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get on air tv show list with invalid api key result error 401`() = runBlockingTest {
        when (val result = access.getOnAirTvShowList(1, "randomInvalidKey")) {
            is Result.Success -> {
                fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    `is`(containsString("invalid"))
                )
            }
        }
    }
}