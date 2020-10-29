package com.example.mymoviddb.authentication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymoviddb.authentication.guest.AuthenticationViewModel
import com.example.mymoviddb.getOrAwaitValue
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.sharedData.FakeAuthenticationAccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AuthenticationViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: IAuthenticationAccess
    private lateinit var authenticationVM: AuthenticationViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = FakeAuthenticationAccess()
        authenticationVM = AuthenticationViewModel(
            ApplicationProvider.getApplicationContext(),
            fakeRemoteSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loginasGuest_successEqualsTrue() = runBlockingTest {
        // WHEN user login login as guest with valid api key
        authenticationVM.loginAsGuest(false)

        // THEN response from server should be [GuestSessionModel] object
        val result = authenticationVM.loginGuestResult.getOrAwaitValue().getContentIfNotHandled()
        if (result is Result.Success) {
            assertThat(result.data?.success, `is`(true))
        } else {
            fail("Testing login as guest with result [success = true] is failed")
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun loginasGuest_resultError401() = runBlockingTest {
        // WHEN user login login as guest with valid api key
        authenticationVM.loginAsGuest(false, "invalidApiKeyV3")

        // THEN response from server should be [GuestSessionModel] object
        val result = authenticationVM.loginGuestResult.getOrAwaitValue().getContentIfNotHandled()
        if (result is Result.Error) {
            assertThat(result.exception.toString().contains("invalid", true), `is`(true))
        } else {
            fail("Testing login as guest with result [Resilt.Error] is failed")
        }
    }
}

