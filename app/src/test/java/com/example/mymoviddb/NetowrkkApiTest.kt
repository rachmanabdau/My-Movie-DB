package com.example.mymoviddb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymoviddb.datasource.remote.NetworkAPI
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.Error401Model
import com.example.mymoviddb.model.NewSessionModel
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.junit.Before
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

    private lateinit var service: NetworkAPI
    private lateinit var failedAdapter: JsonAdapter<Error401Model>


    @Before
    fun setupApi() {
        service = NetworkAPI
        failedAdapter = moshi.adapter(Error401Model::class.java)
    }

    /**
     * Testing requesting a new request token with result success
     */
    @Test
    fun requestToken_getRequestToken_successResultTrue() {
        runBlocking {
            // WHEN user send valid request token
            val tokenRequest = service.retrofitService.getRequestTokenAsync().await().body()

            // THEN success sample from server should equals to true
            assertThat(tokenRequest?.success, Is.`is`(equalTo(true)))
        }
    }

    /**
     * Testing requesting a new request token with result error 401
     */
    @Test
    fun requestToken_getRequestToken_responseError401() {
        runBlocking {
            // WHEN user send invalid request token
            val getRequestToken = service.retrofitService
                .getRequestTokenAsync(apiKey = BuildConfig.V3_AUTH + "invalidToken")
                .await().errorBody()

            // converted error json body
            val requestToken401Result =
                failedAdapter.fromJson(getRequestToken?.string().toString())

            // THEN success result from server equals to false
            assertThat(
                requestToken401Result?.success,
                Is.`is`(equalTo(false))
            )
            // THEN Status code from server equals to 7
            assertThat(
                requestToken401Result?.statusCode,
                Is.`is`(equalTo(7))
            )
        }
    }

    /**
     * Testing login as user with result success
     */
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
            assertThat(userToken.body()?.success, Is.`is`(equalTo(true)))
        }
    }


    /**
     * Testing login as user with result error 401
     */
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
            val login401Result = failedAdapter.fromJson(userToken?.string().toString())

            // THEN login status result is false
            assertThat(login401Result?.success, Is.`is`(equalTo(false)))
            // THEN statusCode is 30
            assertThat(login401Result?.statusCode, Is.`is`(equalTo(30)))
        }
    }

    /**
     * Testing login as guest with result success
     */
    @Test
    fun loginAsGuest_getGuestToken_successStatusTrue() {
        runBlocking {
            // WHEN user create session as guest
            val guestToken = service.retrofitService
                .loginAsGuestAsync(apiKey = BuildConfig.V3_AUTH)
                .await().body()

            // THEN token 'success' result reutrned true
            assertThat(guestToken?.success, Is.`is`(equalTo(true)))
        }
    }


    /**
     * Testing login as guest with result error
     */
    @Test
    fun loginAsGuest_getRequestToken_responseError401() {
        runBlocking {
            // WHEN user send invalid request token
            val getRequestToken = service.retrofitService
                .loginAsGuestAsync(apiKey = BuildConfig.V3_AUTH + "invalidToken")
                .await().errorBody()

            // converted error json body
            val requestToken401Result =
                failedAdapter.fromJson(getRequestToken?.string().toString())

            // THEN success result from server equals to false
            assertThat(
                requestToken401Result?.success,
                Is.`is`(equalTo(false))
            )
            // THEN Status code from server equals to 7
            assertThat(
                requestToken401Result?.statusCode,
                Is.`is`(equalTo(7))
            )
        }
    }

    /**
     * Test createNewSesssion without registered account and valid token request
     * result should be success
     */
    @Test
    fun createNewSession_withoutLoginAccountWithValidToken_resultSuccess() {
        runBlocking {
            // GIVEN new fresh token request without login
            val requestToken =
                service.retrofitService.getRequestTokenAsync().await().body()?.requestToken ?: ""

            // WHEN user createa new session
            val newSession = service.retrofitService.createSeesionAsync(requestToken).await()


            if (newSession.isSuccessful) {
                val result = newSession.body()?.sessionId.toString()

                // THEN session result return with object [NesSessionModel]
                assertThat(result, instanceOf(NewSessionModel::class.java))
                // success value from server should be true
                assertThat(newSession.body()?.success, `is`(true))
            }
        }
    }

    /**
     * creates New Sesssion without registered account and invalid token request
     * result should be Error401
     */
    @Test
    fun createNewSession_withoutLoginAccountInvalidToken_resultError401() {
        runBlocking {
            // GIVEN invalid token request without login
            val requestToken = "invalidKeyDummy"

            // WHEN user createa new session
            val newSession =
                service.retrofitService.createSeesionAsync(requestToken = requestToken).await()

            val recievedJson = newSession.errorBody()?.string().toString()
            val result = failedAdapter.fromJson(recievedJson)

            // THEN session result return with object [NesSessionModel]
            assertThat(result, (instanceOf(Error401Model::class.java)))
            // success value from server should be false
            assertThat(result?.success, `is`(false))
        }
    }

    /**
     * create new sesssion with registered account and valid token request
     * result should be Success
     */
    @Test
    fun createNewSession_withLoginAccountValidToken_resultSuccess() {
        runBlocking {
            // GIVEN valid token request with login
            val requestToken = service.retrofitService.getRequestTokenAsync().await()
                .body()?.requestToken.toString()

            // request token from login
            val loginToken = service.retrofitService.loginAsync(
                "rachamanarifabdau", "92413835", requestToken
            ).await().body()?.requestToken.toString()

            // WHEN user reate new session
            val newSession =
                service.retrofitService.createSeesionAsync(requestToken = loginToken).await()

            val recievedJson = newSession.errorBody()?.string().toString()
            val result = failedAdapter.fromJson(recievedJson)

            // THEN session result return with object [NesSessionModel]
            assertThat(result, (instanceOf(Error401Model::class.java)))
            // success value from server should be false
            assertThat(result?.success, `is`(false))
        }
    }

    /**
     * creates new sesssion with registered account and invalid token request
     * result should be Error401
     */
    @Test
    fun createNewSession_withLoginAccountInvalidToken_resultSuccess() {
        runBlocking {
            // GIVEN invalid token request with login
            val loginToken = "invalidTokenDummy"

            // WHEN user has request token and create new session
            val newSession =
                service.retrofitService.createSeesionAsync(requestToken = loginToken).await()

            val recievedJson = newSession.errorBody()?.string().toString()
            val result = failedAdapter.fromJson(recievedJson)

            // THEN session result return with object [NesSessionModel]
            assertThat(result, (instanceOf(Error401Model::class.java)))
            // success value from server should be false
            assertThat(result?.success, `is`(false))
        }
    }
}