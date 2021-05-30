package com.example.mymoviddb.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.FakeRemoteServer
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail
import com.example.mymoviddb.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.*

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var fakeRemoteSource: IMainAccess
    private lateinit var mainViewmodel: MainViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setupViewModel() {
        Dispatchers.setMain(mainThreadSurrogate)
        fakeRemoteSource = MainRepository(FakeRemoteServer())
        mainViewmodel = MainViewModel(fakeRemoteSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `get user detail with valid session id result success`() = runBlockingTest {
        mainViewmodel.getUserDetail(FakeRemoteServer.sampleSessionId, BuildConfig.V3_AUTH)
        val expectedResult = UserDetail(
            avatar = UserDetail.Avatar(
                UserDetail.Avatar.Gravatar(hash = "c4bc3a0eae31820916cfbabf17cb29a9"),
                UserDetail.Avatar.Tmdb(avatarPath = "/3bk7ZEoQ1XU5dJSe1qp6oJxEJMd.jpg")
            ),
            id = 8781441,
            iso6391 = "en",
            iso31661 = "US",
            name = "",
            includeAdult = false,
            username = "rachamanabdau"
        )

        when (val result = mainViewmodel.userDetail.getOrAwaitValue()) {
            is Result.Success -> {
                MatcherAssert.assertThat(result.data, CoreMatchers.notNullValue())
                MatcherAssert.assertThat(result.data, `is`(expectedResult))
            }

            is Result.Loading -> {
            }

            is Result.Error -> {
                Assert.fail("testing get user detail with result success is failed")
            }
        }
    }

    @Test
    fun `get user detail with invalid session id result error`() = runBlockingTest {
        mainViewmodel.getUserDetail("FakeRemoteServer.sampleSessionId", BuildConfig.V3_AUTH)
        val expectedErrorMessage =
            "Authentication failed: You do not have permissions to access the service."

        when (val result = mainViewmodel.userDetail.getOrAwaitValue()) {
            is Result.Success -> {
                Assert.fail("testing get user detail with result success is failed")
            }

            is Result.Loading -> {
            }

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage,
                    `is`(expectedErrorMessage)
                )
            }
        }
    }

    @Test
    fun `logout with valid session id result success`() = runBlockingTest {
        mainViewmodel.logout(FakeRemoteServer.sampleSessionId, BuildConfig.V3_AUTH)
        val expectedResult =
            ResponsedBackend(success = true, statusMessage = null, statusCode = null)

        when (val result = mainViewmodel.logoutResult.getOrAwaitValue()) {
            is Result.Success -> {
                MatcherAssert.assertThat(result.data, CoreMatchers.notNullValue())
                MatcherAssert.assertThat(result.data, `is`(expectedResult))
            }

            is Result.Loading -> {
            }

            is Result.Error -> {
                Assert.fail("testing get user detail with result success is failed")
            }
        }
    }

    @Test
    fun `logout with invalid session id result error`() = runBlockingTest {
        mainViewmodel.logout("FakeRemoteServer.sampleSessionId", BuildConfig.V3_AUTH)
        val expectedErrorMessage = "Invalid id: The pre-requisite id is invalid or not found."

        when (val result = mainViewmodel.logoutResult.getOrAwaitValue()) {
            is Result.Success -> {
                Assert.fail("testing get user detail with result success is failed")
            }

            is Result.Loading -> {
            }

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage,
                    `is`(expectedErrorMessage)
                )
            }
        }
    }
}