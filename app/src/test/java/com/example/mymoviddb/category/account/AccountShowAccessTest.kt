package com.example.mymoviddb.category.account

import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.ResponsedBackend
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AccountShowAccessTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<ResponsedBackend>
    private lateinit var access: IAccountShowAccess

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = AccountShowAccess(fakeRemoteSource)
        errorConverter = moshi.adapter(ResponsedBackend::class.java)
    }

    @Test
    fun `get favourite TV Shows result success`() = runBlockingTest {

        when (val result = access.getFavouriteTVShows(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            FavouriteAndWatchListShow::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get favourite tv shows with invalid session id result error`() = runBlockingTest{
        when (val result = access.getFavouriteTVShows(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                fail("get user detail with expexted result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }

    @Test
    fun `get favourite Movies result success`() = runBlockingTest {

        when (val result = access.getFavouriteMovies(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            FavouriteAndWatchListShow::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get favourite Movies with invalid session id result error`() = runBlockingTest{
        when (val result = access.getFavouriteTVShows(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                fail("get user detail with expexted result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }

    @Test
    fun `get watchlist TV Shows result success`() = runBlockingTest {

        when (val result = access.getWatchlistTVShows(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            FavouriteAndWatchListShow::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get watch list tv shows with invalid session id result error`() = runBlockingTest{
        when (val result = access.getWatchlistTVShows(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                fail("get user detail with expexted result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }

    @Test
    fun `get watch list Movies result success`() = runBlockingTest {

        when (val result = access.getWatchlistMovies(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            FavouriteAndWatchListShow::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get watch list Movies with invalid session id result error`() = runBlockingTest{

        when (val result = access.getWatchlistMovies(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                fail("get user detail with expexted result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }
}