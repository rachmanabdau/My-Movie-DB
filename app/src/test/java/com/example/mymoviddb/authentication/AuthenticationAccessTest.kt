package com.example.mymoviddb.authentication

import com.example.mymoviddb.datasource.remote.FakeRemoteServer
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.Error401Model
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.Result
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class AuthenticationAccessTest {


    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<Error401Model>
    private lateinit var access: AuthenticationAccess
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = FakeRemoteServer()
        access = AuthenticationAccess(fakeRemoteSource)
        errorConverter = moshi.adapter(Error401Model::class.java)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `login as guest with valid token result success`() = runBlockingTest {

        when (val request = access.loginAsGuest()) {
            is Result.Success -> {
                assertThat(request.data, `is`(notNullValue(GuestSessionModel::class.java)))
                assertThat(request.data?.success, `is`(true))
            }

            is Result.Loading -> {
            }// Do nothing while fetching data or waiting for result{)

            is Result.Error -> fail("Testing login with result success is failed")
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `login as guest with invalid token result error 401`() = runBlockingTest {

        when (val request = access.loginAsGuest("invalidkey")) {
            is Result.Success -> {
                fail("Testing login with result success is failed")
            }

            is Result.Loading -> {
            }// Do nothing while fetching data or waiting for result{)

            is Result.Error -> {
                assertThat(
                    request.exception.localizedMessage?.toLowerCase(),
                    `is`(containsString("invalid"))
                )
            }
        }
    }
}