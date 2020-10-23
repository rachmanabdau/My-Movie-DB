package com.example.mymoviddb.datasource.remote

import com.example.mymoviddb.model.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class RemoteServerAccessTest {

    private lateinit var accessServer: RemoteServer

    @Before
    fun setuoRemoteServer() {
        accessServer = RemoteServerAccess()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTokenRequest_resultSuccessTrue() = runBlocking {
        // WHEN user request for a request token
        val tokenRequest = accessServer.requestAccessToken()

        // THEN 'Success' value from server equals to true
        if (tokenRequest is Result.Success) {
            assertThat(tokenRequest.data?.success, `is`(true))
        } else {
            fail("Response feom server is not 'Success'")
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun login_resultSuccessTrue() = runBlocking {
        // WHEN user login with account
        val tokenRequest = accessServer.requestAccessToken()
        val tokenResult = if (tokenRequest is Result.Success) {
            tokenRequest.data
        } else {
            null
        }
        val loginAccount = accessServer.loginAsUser("rachamanabdau", "92413835", tokenResult)

        // THEN 'Success' value from server equals to true
        if (loginAccount is Result.Success) {
            assertThat(loginAccount.data?.success, `is`(true))
        } else {
            val errorMessage = loginAccount as Result.Error
            fail(errorMessage.toString())
        }
    }
}