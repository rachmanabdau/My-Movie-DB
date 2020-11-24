package com.example.mymoviddb.authentication.user

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mymoviddb.authentication.AuthenticationAccess
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.getOrAwaitValue
import com.example.mymoviddb.sharedData.FakeRemoteServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP, Build.VERSION_CODES.M, Build.VERSION_CODES.N, Build.VERSION_CODES.O, Build.VERSION_CODES.P])
class UserViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: IAuthenticationAccess
    private lateinit var userViewModel: UserViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = AuthenticationAccess(FakeRemoteServer())
        userViewModel = UserViewModel(fakeRemoteSource, ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `login with valid username and valid password result success`() = runBlockingTest {
        userViewModel.login("rachmanabdau", "123456")
        val result = userViewModel.loginResult.getOrAwaitValue().getContentIfNotHandled()

        assertThat(result, `is`("success"))
    }

    @Test
    fun `login with unregistered username and wrong password result success`() = runBlockingTest {
        userViewModel.login("rachamanabdau", "92413835")
        val result = userViewModel.loginResult.getOrAwaitValue().getContentIfNotHandled()

        assertThat(
            result,
            `is`("Invalid username and/or password: You did not provide a valid login.")
        )
    }
}