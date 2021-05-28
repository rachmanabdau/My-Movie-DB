package com.example.mymoviddb.authentication

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.utils.preference.Preference
import com.example.mymoviddb.login.ILoginAccess
import com.example.mymoviddb.login.LoginRepository
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.example.mymoviddb.sharedData.FakeUserPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
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
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
class AuthenticationViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: ILoginAccess
    private lateinit var authenticationVM: AuthenticationViewModel
    private lateinit var userPreference: Preference
    private val mainThreadSurrogate = TestCoroutineDispatcher()

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = LoginRepository(FakeRemoteServer())
        userPreference = FakeUserPreference()
        authenticationVM = AuthenticationViewModel(
            userPreference,
            fakeRemoteSource
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.cleanupTestCoroutines()
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
        val result = authenticationVM.loginGuestResult.getOrAwaitValue().peekContent()
        if (result is Result.Error) {
            assertThat(result.exception.toString().contains("invalid", true), `is`(true))
        } else {
            fail("Testing login as guest with result [Resilt.Error] is failed")
        }
    }
}

