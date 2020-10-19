package com.example.mymoviddb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymoviddb.datasource.remote.NetworkAPI
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.Error401Model
import kotlinx.coroutines.runBlocking
import okio.BufferedSource
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

// Change run configuration jdk to version 9 (Required by roboelectric)
@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class NetowrkkApiTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val service = NetworkAPI

    // Converter for error json body
    val failedAdapter = moshi.adapter(Error401Model::class.java)

    @Test
    fun requestToken_getRequestToken_successResultTrue() {
        runBlocking {
            // WHEN user send valid request token
            val tokenRequest = service.retrofitService.getRequestTokenAsync().await().body()

            // THEN success sample from server should equals to true
            MatcherAssert.assertThat(tokenRequest?.success, Is.`is`(CoreMatchers.equalTo(true)))
        }
    }

    @Test
    fun requestToken_getRequestToken_responseError401() {
        runBlocking {
            // WHEN user send invalid request token
            val getRequestToken = service.retrofitService
                .getRequestTokenAsync(apiKey = BuildConfig.V3_AUTH + "invalidToken")
                .await().errorBody()

            // converted error json body
            val requestToken401Result =
                failedAdapter.fromJson(getRequestToken?.source() as BufferedSource)

            // THEN success result from server equals to false
            MatcherAssert.assertThat(
                requestToken401Result?.success,
                Is.`is`(CoreMatchers.equalTo(false))
            )
            // THEN Status code from server equals to 7
            MatcherAssert.assertThat(
                requestToken401Result?.statusCode,
                Is.`is`(CoreMatchers.equalTo(7))
            )
        }
    }

    @Test
    fun loginAsUser_getTokenFromLogin_successStatusTrue() {
        runBlocking {
            // WHEN user create request token and login
            val tokenRequest =
                service.retrofitService.getRequestTokenAsync().await().body()?.requestToken ?: ""
            val userToken = service.retrofitService.loginAsync(
                requestToken = tokenRequest,
                username = "rachamanabdau", password = "92413835"
            ).await()

            // THEN login status result is true
            MatcherAssert.assertThat(userToken.body()?.success, Is.`is`(CoreMatchers.equalTo(true)))
        }
    }

    @Test
    fun loginAsUser_getTokenFromLogin_responseError401() {
        runBlocking {
            // WHEN user create request token and login
            val tokenRequest =
                service.retrofitService.getRequestTokenAsync().await().body()?.requestToken ?: ""
            val userToken = service.retrofitService.loginAsync(
                requestToken = tokenRequest,
                username = "rachmanabdau", password = "92413835"
            ).await().errorBody()

            // converted error json body
            val login401Result = failedAdapter.fromJson(userToken?.source() as BufferedSource)

            // THEN login status result is false
            MatcherAssert.assertThat(login401Result?.success, Is.`is`(CoreMatchers.equalTo(false)))
            // THEN statusCode is 30
            MatcherAssert.assertThat(login401Result?.statusCode, Is.`is`(CoreMatchers.equalTo(30)))
        }
    }

    @Test
    fun loginAsGuest_getGuestToken_successStatusTrue() {
        runBlocking {
            // WHEN user create session as guest
            val guestToken = service.retrofitService
                .loginAsGuestAsync(apiKey = BuildConfig.V3_AUTH + "invalidToken")
                .await().body()

            // THEN token 'success' result reutrned true
            MatcherAssert.assertThat(guestToken?.success, Is.`is`(CoreMatchers.equalTo(true)))
        }
    }

    @Test
    fun loginAsGuest_getRequestToken_responseError401() {
        runBlocking {
            // WHEN user send invalid request token
            val getRequestToken = service.retrofitService
                .loginAsGuestAsync(apiKey = BuildConfig.V3_AUTH + "invalidToken")
                .await().errorBody()

            // converted error json body
            val requestToken401Result =
                failedAdapter.fromJson(getRequestToken?.source() as BufferedSource)

            // THEN success result from server equals to false
            MatcherAssert.assertThat(
                requestToken401Result?.success,
                Is.`is`(CoreMatchers.equalTo(false))
            )
            // THEN Status code from server equals to 7
            MatcherAssert.assertThat(
                requestToken401Result?.statusCode,
                Is.`is`(CoreMatchers.equalTo(7))
            )
        }
    }
}