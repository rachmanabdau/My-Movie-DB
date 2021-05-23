package com.example.mymoviddb.main

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.datasource.remote.moshi
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail
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
class MainRepositoryTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<ResponsedBackend>
    private lateinit var access: IMainAccess

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = MainRepository(fakeRemoteSource)
        errorConverter = moshi.adapter(ResponsedBackend::class.java)
    }

    @Test
    fun `get user detail with valid api key and valid session id result success`() =
        runBlockingTest {
            when (val result =
                access.getUserDetail(FakeRemoteServer.sampleSessionId, BuildConfig.V3_AUTH)) {
                is Result.Success -> {
                    MatcherAssert.assertThat(
                        result.data, CoreMatchers.`is`(
                            CoreMatchers.notNullValue(
                                UserDetail::class.java
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
    fun `get user detail with valid api key and invalid session id result error 401`() =
        runBlockingTest {
            when (val result = access.getUserDetail("invalid session id", BuildConfig.V3_AUTH)) {
                is Result.Success -> {
                    fail("get user detail with expexted result 401 Error is failed")
                }

                is Result.Loading -> {
                }// Do nothing just wait for the result

                is Result.Error -> {
                    MatcherAssert.assertThat(
                        result.exception.localizedMessage, CoreMatchers.`is`(
                            CoreMatchers.`is`(CoreMatchers.containsString("You do not have permission"))
                        )
                    )
                }
            }
        }

    @Test
    fun `get user detail with invalid api key and valid session id result error 401`() =
        runBlockingTest {
            when (val result =
                access.getUserDetail(FakeRemoteServer.sampleSessionId, "invalid api key")) {
                is Result.Success -> {
                    fail("get user detail with expexted result 401 Error is failed")
                }

                is Result.Loading -> {
                }// Do nothing just wait for the result

                is Result.Error -> {
                    MatcherAssert.assertThat(
                        result.exception.localizedMessage, CoreMatchers.`is`(
                            CoreMatchers.`is`(CoreMatchers.containsString("You do not have permission"))
                        )
                    )
                }
            }
        }

    @Test
    fun `logout with valid api key and valid session id result success`() =
        runBlockingTest {
            val sessionId = mapOf("session_id" to FakeRemoteServer.sampleSessionId)
            when (val result =
                access.logout(sessionId, BuildConfig.V3_AUTH)) {
                is Result.Success -> {
                    MatcherAssert.assertThat(
                        result.data, CoreMatchers.`is`(
                            CoreMatchers.notNullValue(
                                ResponsedBackend::class.java
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
    fun `logout with invalid session id result error 401`() =
        runBlockingTest {
            val sessionId = mapOf("session_id" to "invalid session id")
            when (val result =
                access.logout(sessionId, BuildConfig.V3_AUTH)) {
                is Result.Success -> {
                    fail("get popular movie list with valid api key result success` test failed")
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