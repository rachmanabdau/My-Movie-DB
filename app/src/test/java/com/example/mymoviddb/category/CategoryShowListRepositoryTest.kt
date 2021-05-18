package com.example.mymoviddb.category

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.remote.NetworkService
import com.example.mymoviddb.core.remote.moshi
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class CategoryShowListRepositoryTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<ResponsedBackend>
    private lateinit var repository: CategoryShowListRepository

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        repository = CategoryShowListRepository(fakeRemoteSource)
        errorConverter = moshi.adapter(ResponsedBackend::class.java)
    }

    @Test
    fun getPopularMovie_resultSuccess() = runBlockingTest {
        // GIVEN popular movie on page one with valid API Key V3
        // WHEN we get popular movie from server
        when (val result = repository.getPopularMovieList(1, BuildConfig.V3_AUTH)) {
            // THEN Result should be success with class type of PreviewMovie
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewMovie::class.java))
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
    fun getPopularMovie_resultError401() = runBlockingTest {
        // GIVEN paramter to get popular movie on page one and invalid api key v3
        // WHEN get poplar movie from server
        when (val result = repository.getPopularMovieList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error with mesaage contins word "invalid" (401 : Unauthorized)
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun getPlayingNowMovies_resultSuccess() = runBlockingTest {
        // GIVEN paramter to get popular movie on page one and valid api key v3
        // WHEN get now playing movie from server
        when (val result = repository.getNowPlayingMovieList(1, BuildConfig.V3_AUTH)) {
            // THEN Result success wtih class type Preview Movie
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewMovie::class.java))
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
    fun getPlayingNowMovies_resultError401() = runBlockingTest {
        // GIVEN parameter to get popular movie on page one and invalid api key v3
        // WHEN get now playing movie from server
        when (val result = repository.getNowPlayingMovieList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error with mesaage contins word "invalid" (401 : Unauthorized)
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun searchMovie_resultSuccess() = runBlockingTest {
        // GIVEN parameter to search movie with title "Matrix"
        // WHEN search movie from server with valid API v3 key
        when (val result = repository.searchMovies("Matrix", 1, BuildConfig.V3_AUTH)) {
            // THEN resule should not be empty and have class type of PreviewMovie
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewMovie::class.java))
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
    fun searchMovie_resultSuccessEmpty() = runBlockingTest {
        // GIVEN parameter to search movie with empty title
        // WHEN search movie from server with valid API v3 key
        when (val result = repository.searchMovies("", 1, BuildConfig.V3_AUTH)) {
            // THEN result should be empty
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewMovie::class.java))
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
    fun searchMovie_resultError401() = runBlockingTest {
        // GIVEN parameter to get popular movie on page one and invalid api key v3
        // WHEN user search movie
        when (val result = repository.searchMovies("", 1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error with mesaage contins word "invalid" (401 : Unauthorized)
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun getFavouriteMovies_resultSuccess() = runBlockingTest {
        // GIVEN parameter to get favourite movies
        // WHEN search movie from server with valid API v3 key
        when (val result = repository.getFavouriteMovies(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            // THEN resule should not be empty and have class type of PreviewMovie
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            PreviewMovie::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get favourite Movies with invalid session id result error`() = runBlockingTest {
        // GIVEN parameter to get favourite movie on page one and invalid api key v3
        // WHEN user get their favourite movie
        when (val result = repository.getFavouriteTVShows(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                Assert.fail("get user detail with expected result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN the result should be error with mssage contins "Invalid id..." (401 Unauthorized)
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }

    @Test
    fun `get watch list Movies result success`() = runBlockingTest {
        // GIVEN get watch list movie with parameter page = 1 and valid API key v3
        // WHEN user get their watch list movie
        when (val result = repository.getWatchlistMovies(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            // THEN result should be success with class type of PreviewMovie
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            PreviewMovie::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get watch list Movies with invalid session id result error`() = runBlockingTest {
        // GIVEN get wach lost movie in page one with invalid API key v3
        // WHEN user get wathc list from server
        when (val result = repository.getWatchlistMovies(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                Assert.fail("get user detail with expected result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN the result should be error and message contiains words "Invalid id..."
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }

    @Test
    fun getPopularTvShows_resultSuccess() = runBlockingTest {
        // GIVEN get popular TV Shows in page 1 with valid API key v3
        // WHEN user get popular tv shows
        when (val result = repository.getPopularTvShowList(1, BuildConfig.V3_AUTH)) {
            // THEM result success with class type of PreviewTvShow
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewTvShow::class.java))
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
    fun getPopularTvShows_resultError401() = runBlockingTest {
        // GIVEN ge popular TV shows in page one and invalid API key v3
        // WHEN user get popular TV Shows from server
        when (val result = repository.getPopularTvShowList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error with message contains word "invalid" (401: "Unauthorized")
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun getOnAirTvShows_resultSuccess() = runBlockingTest {
        // GIVEN request on air tc hsows with in page one and valid API key v3
        // WHEN user get on air tv show from server
        when (val result = repository.getOnAirTvShowList(1, BuildConfig.V3_AUTH)) {
            // THEN result shoud be success and class type of PreviewTvShow
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewTvShow::class.java))
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
    fun getOnAirTvShows_resultError401() = runBlockingTest {
        // GIVEN request get on air tv shows in page one with invalid API key v3
        // WHEN user sending request to server
        when (val result = repository.getOnAirTvShowList(1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error 401 with message contains word "invalid"
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun searchTvShow_resultSuccessNotEmpty() = runBlockingTest {
        // GIVEN search tv show with title "The Simpsons" in page 1 with valid API key v3
        // WHEN user get search tv show from server
        when (val result = repository.searchTvShowList("The Simpsons", 1, BuildConfig.V3_AUTH)) {
            // THEN result should be success and not empty with class type of PreviewTvShow
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewTvShow::class.java))
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
        // GIVEN search tv show with empty title in page 1 with valid API key v3
        // WHEN user get search tv show from server
        when (val result = repository.searchTvShowList("", 1, BuildConfig.V3_AUTH)) {
            // THEN result should be success and empty with class type of PreviewTvShow
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data,
                    CoreMatchers.`is`(CoreMatchers.notNullValue(PreviewTvShow::class.java))
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
        // GIVEN parameter to search movie with title "The Simpsons" page one and invalid api key v3
        // WHEN user search movie
        when (val result = repository.searchTvShowList("The Simpsons", 1, "BuildConfig.V3_AUTH")) {
            is Result.Success -> {
                Assert.fail("`get popular movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error 401 and message contains word "invalid"
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage?.lowercase(),
                    CoreMatchers.`is`(CoreMatchers.containsString("invalid"))
                )
            }
        }
    }

    @Test
    fun getFavouriteTvShows_resultSuccess() = runBlockingTest {
        // GIVEN request to get favourite Tv show in page one with valid API key v3
        // WHEN user make a request to server
        when (val result = repository.getFavouriteTVShows(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            // THEN result should be success with class type of PreviewTvShow
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            PreviewTvShow::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get favourite tv shows with invalid session id result error`() = runBlockingTest {
        // GIVEN request to get favourite tv show with invalid api key
        // WHEN make request to server
        when (val result = repository.getFavouriteTVShows(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                Assert.fail("get user detail with expected result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error 401 with message contains "Invalid id.."
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }

    @Test
    fun getWatchListTvShows_resultSuccess() = runBlockingTest {
        // GIVEN get watch list tv show in page one with valid API key v3
        // WHEN user send the request
        when (val result = repository.getWatchlistTVShows(
            accountId = 1, sessionId = FakeRemoteServer.sampleSessionId,
            page = 1
        )) {
            // THEN result shoudl be success with type class of PrevewTvShow
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            PreviewTvShow::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                Assert.fail("get popular movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get watch list tv shows with invalid session id result error`() = runBlockingTest {
        // GIVEN request to get watch list tv show in page 1 with invalid API key v3
        // WHEN user send the request
        when (val result = repository.getWatchlistTVShows(
            accountId = 1, sessionId = "invalid session id",
            page = 1
        )) {
            is Result.Success -> {
                Assert.fail("get user detail with expected result 401 Error is failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            // THEN result should be error 401 with message contains "Invalid id..."
            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid id"))
                    )
                )
            }
        }
    }
}