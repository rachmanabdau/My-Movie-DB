package com.example.mymoviddb.authentication.user

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymoviddb.getOrAwaitValue
import com.example.mymoviddb.login.ILoginAccess
import com.example.mymoviddb.login.LoginRepository
import com.example.mymoviddb.login.LoginViewModel
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.example.mymoviddb.sharedData.FakeUserPreference
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
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP, Build.VERSION_CODES.M, Build.VERSION_CODES.N, Build.VERSION_CODES.O, Build.VERSION_CODES.P])
class LoginViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: ILoginAccess
    private lateinit var userViewModel: LoginViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = LoginRepository(FakeRemoteServer())
        userViewModel = LoginViewModel(fakeRemoteSource, FakeUserPreference())
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
        userViewModel.login("rachamanabdau", "654321")
        val result = userViewModel.loginResult.getOrAwaitValue().getContentIfNotHandled()

        assertThat(
            result,
            `is`("Invalid username and/or password: You did not provide a valid login.")
        )
    }
}