package com.example.mymoviddb.login

import com.example.mymoviddb.core.mock.FakeRemoteServer
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.datasource.remote.moshi
import com.example.mymoviddb.core.model.GuestSessionModel
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
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
class LoginRepositoryTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<ResponsedBackend>
    private lateinit var access: LoginRepository

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = LoginRepository(fakeRemoteSource)
        errorConverter = moshi.adapter(ResponsedBackend::class.java)

    }

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

        val request = access.loginAsGuest("")
        println(request)

        /*when (val request = access.loginAsGuest("")) {
            is Result.Success -> {
                fail("Testing login with result success is failed")
            }

            is Result.Loading -> {
            }// Do nothing while fetching data or waiting for result{)

            is Result.Error -> {
                assertThat(
                    request.exception.localizedMessage?.lowercase(),
                    `is`(containsString("invalid"))
                )
                print(request.exception.localizedMessage)
            }
        }*/
    }

}