package com.example.mymoviddb.category.tv

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.Error401Model
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryTVListIAccessTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<Error401Model>
    private lateinit var access: ICategoryTVListAccess

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = CategoryTVListIAccess(fakeRemoteSource)
        errorConverter = moshi.adapter(Error401Model::class.java)
    }

    @Test
    fun `get popular tv shows list result success`() = runBlockingTest {
        when (val result = access.getPopularTvShowList(1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(TVShowModel::class.java))
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get popular tv shows list result error 401`() = runBlockingTest {
        when (val result = access.getPopularTvShowList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.toLowerCase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `get on air tvh shows list result success`() = runBlockingTest {
        when (val result = access.getOnAirTvShowList(1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(TVShowModel::class.java))
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get on air tv shows list result error 401`() = runBlockingTest {
        when (val result = access.getOnAirTvShowList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.toLowerCase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun `search tv shows result success`() = runBlockingTest {
        when (val result = access.searchTvShowList("Matrix", 1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(TVShowModel::class.java))
                )
                MatcherAssert.assertThat(
                    result.data?.results?.isNotEmpty(),
                    CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `search movies list result success list is empty`() = runBlockingTest {
        when (val result = access.searchTvShowList("", 1, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(TVShowModel::class.java))
                )
                MatcherAssert.assertThat(
                    result.data?.results?.isEmpty(),
                    CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `search movies list result error 401`() = runBlockingTest {
        when (val result = access.searchTvShowList("", 1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.toLowerCase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

}
