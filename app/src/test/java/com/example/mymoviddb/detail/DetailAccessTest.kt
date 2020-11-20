package com.example.mymoviddb.detail

import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.*
import com.example.mymoviddb.sharedData.FakeRemoteServer
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class DetailAccessTest {

    private lateinit var fakeRemoteSource: NetworkService
    private lateinit var errorConverter: JsonAdapter<ResponsedBackend>
    private lateinit var access: IDetailAccess

    @Before
    fun setupViewModel() {
        fakeRemoteSource = FakeRemoteServer()
        access = DetailAccess(fakeRemoteSource)
        errorConverter = moshi.adapter(ResponsedBackend::class.java)
    }

    @Test
    fun `get detail movie with existed id result success`() = runBlockingTest {

        when (val result = access.getDetailMovie(741067L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            MovieDetail::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get detail movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get detail movie with id does not exist result error404`() = runBlockingTest {

        when (val result = access.getDetailMovie(0L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                fail("get detail movie list with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("The resource you requested could not be found."))
                    )
                )
            }
        }
    }

    @Test
    fun `get detail movie with invalid api key result error401`() = runBlockingTest {

        when (val result = access.getDetailMovie(0L, "invalid api key")) {
            is Result.Success -> {
                fail("get detial movie list with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid API key"))
                    )
                )
            }
        }
    }

    @Test
    fun `get recommendation movies with existed id result success`() = runBlockingTest {

        when (val result = access.getRecommendationMovies(741067L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            MovieModel::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get recommendation movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get recommendation movies with id does not exist result empty`() = runBlockingTest {

        when (val result = access.getRecommendationMovies(0L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data?.results?.isEmpty(), CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get recommendation movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get recommendation movies with invalid api key result error401`() = runBlockingTest {

        when (val result = access.getDetailMovie(0L, "invalid api key")) {
            is Result.Success -> {
                fail("get recommendation movie list with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid API key"))
                    )
                )
            }
        }
    }

    @Test
    fun `get similar movies with existed id result success`() = runBlockingTest {

        when (val result = access.getSimilarMovies(741067L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            MovieModel::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get similar movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get similar movies with id does not exist result empty`() = runBlockingTest {

        when (val result = access.getSimilarMovies(0L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data?.results?.isEmpty(), CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get similar movie list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get similar movies with invalid api key result error401`() = runBlockingTest {

        when (val result = access.getSimilarMovies(0L, "invlaid api key")) {
            is Result.Success -> {
                fail("get similar movie list with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid API key"))
                    )
                )
            }
        }
    }

    @Test
    fun `get movie account state result success`() = runBlockingTest {

        when (val result = access.getMovieAuthState(
            741067L,
            FakeRemoteServer.sampleSessionId,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            MediaState::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get movie account state valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get movie account state result error401`() = runBlockingTest {

        when (val result = access.getMovieAuthState(
            741067L,
            "FakeRemoteServer.sampleSessionId",
            "invalid api key"
        )) {
            is Result.Success -> {
                fail("get movie account state with invlaid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Authentication failed"))
                    )
                )
            }
        }
    }

    @Test
    fun `mark movie as favourite result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(741067L, "movie", true, null)

        when (val result = access.markAsFavorite(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(12)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("mark movie as favourite with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `remove movie from favourite result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(741067L, "movie", false, null)

        when (val result = access.markAsFavorite(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(13)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("remove movie from favourite  with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `add movie as favourite with invalid api key and invalid session id result error401`() =
        runBlockingTest {
            val mediaType = MarkMediaAs(741067L, "movie", false, null)

            when (val result = access.markAsFavorite(
                123654,
                "FakeRemoteServer.sampleSessionId",
                mediaType,
                "invalid api key"
            )) {
                is Result.Success -> {
                    fail("add movie as favourite with invalid api key result error test failed")
                }

                is Result.Loading -> {
                }// Do nothing just wait for the result

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
    fun `add movie to watch list result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(741067L, "movie", null, true)

        when (val result = access.addToWatchList(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(12)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("gadd movie to watch list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `remove movie from watch list result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(741067L, "movie", null, false)

        when (val result = access.addToWatchList(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(13)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("add movie to watch list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `add movie to watch list with invalid api key and invalid session id result error401`() =
        runBlockingTest {
            val mediaType = MarkMediaAs(741067L, "movie", false, null)

            when (val result = access.markAsFavorite(
                123654,
                "FakeRemoteServer.sampleSessionId",
                mediaType,
                "invalid api key"
            )) {
                is Result.Success -> {
                    fail("add movie to watch list with invalid api key result error test failed")
                }

                is Result.Loading -> {
                }// Do nothing just wait for the result

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
    fun `get detail tv show with existed id result success`() = runBlockingTest {

        when (val result = access.getDetailTV(62286L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            TVDetail::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("gget detail tv show with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get detail tv show with id does not exist result error404`() = runBlockingTest {

        when (val result = access.getDetailTV(0L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                fail("gget detail tv show with valid api key result success` test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("The resource you requested could not be found."))
                    )
                )
            }
        }
    }

    @Test
    fun `get detail tv show with invalid api key result error401`() = runBlockingTest {

        when (val result = access.getDetailTV(0L, "invlaid api key")) {
            is Result.Success -> {
                fail("get detail tv show with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid API key"))
                    )
                )
            }
        }
    }

    @Test
    fun `get recommendation tv shows with existed id result success`() = runBlockingTest {

        when (val result = access.getRecommendationTVShows(62286L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            TVShowModel::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get recommendation tv shows with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get recommendation tv shows with id does not exist result empty`() = runBlockingTest {

        when (val result = access.getRecommendationMovies(0L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data?.results?.isEmpty(), CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get recommendation tv shows with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get recommendation tv shows with invalid api key result error401`() = runBlockingTest {

        when (val result = access.getDetailMovie(0L, "invlaid api key")) {
            is Result.Success -> {
                fail("get recommendation tv shows with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid API key"))
                    )
                )
            }
        }
    }

    @Test
    fun `get similar tv shows with existed id result success`() = runBlockingTest {

        when (val result = access.getSimilarTVShows(741067L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            TVShowModel::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("gget similar tv shows with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get similar tv shows with id does not exist result empty`() = runBlockingTest {

        when (val result = access.getSimilarTVShows(0L, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data?.results?.isEmpty(), CoreMatchers.`is`(true)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("gget similar tv shows with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get similar tv shows with invalid api key result error401`() = runBlockingTest {

        when (val result = access.getSimilarTVShows(0L, "invlaid api key")) {
            is Result.Success -> {
                fail("get similar tv shows with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Invalid API key"))
                    )
                )
            }
        }
    }

    @Test
    fun `get tv show account state result success`() = runBlockingTest {

        when (val result =
            access.getTVAuthState(62286L, FakeRemoteServer.sampleSessionId, BuildConfig.V3_AUTH)) {
            is Result.Success -> {
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            MediaState::class.java
                        )
                    )
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("get tv show account state with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `get tv show account state result error401`() = runBlockingTest {

        when (val result =
            access.getTVAuthState(62286L, "FakeRemoteServer.sampleSessionId", "invalid api key")) {
            is Result.Success -> {
                fail("get tv show account state with invalid api key result error test failed")
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                MatcherAssert.assertThat(
                    result.exception.localizedMessage, CoreMatchers.`is`(
                        CoreMatchers.`is`(CoreMatchers.containsString("Authentication failed"))
                    )
                )
            }
        }
    }

    @Test
    fun `mark tv show as favourite result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(62286L, "tv", true, null)

        when (val result = access.markAsFavorite(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(12)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("mark tv show as favourite with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `remove tv show from favourite result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(62286L, "tv", false, null)

        when (val result = access.markAsFavorite(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(13)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("remove tv show from favourite with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `add tv show as favourite with invalid api key and invalid session id result error401`() =
        runBlockingTest {
            val mediaType = MarkMediaAs(62286L, "tv", false, null)

            when (val result = access.markAsFavorite(
                123654,
                "FakeRemoteServer.sampleSessionId",
                mediaType,
                "invalid api key"
            )) {
                is Result.Success -> {
                    fail("add tv show as favourite with invalid api key and invalid session result error` test failed")
                }

                is Result.Loading -> {
                }// Do nothing just wait for the result

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
    fun `add tv show to watch list result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(62286L, "tv", null, true)

        when (val result = access.addToWatchList(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(12)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("add tv show to watch list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `remove tv show from watch list result success`() = runBlockingTest {
        val mediaType = MarkMediaAs(741067L, "tv", null, false)

        when (val result = access.addToWatchList(
            123654,
            FakeRemoteServer.sampleSessionId,
            mediaType,
            BuildConfig.V3_AUTH
        )) {
            is Result.Success -> {
                // result is not null
                MatcherAssert.assertThat(
                    result.data, CoreMatchers.`is`(
                        CoreMatchers.notNullValue(
                            ResponsedBackend::class.java
                        )
                    )
                )
                // status code shlod be 12
                MatcherAssert.assertThat(
                    result.data?.statusCode, CoreMatchers.`is`(13)
                )
            }

            is Result.Loading -> {
            }// Do nothing just wait for the result

            is Result.Error -> {
                fail("remove tv show from watch list with valid api key result success` test failed")
            }
        }
    }

    @Test
    fun `add tv show to watch list with invalid api key and invalid session id result error401`() =
        runBlockingTest {
            val mediaType = MarkMediaAs(741067L, "tv", null, true)

            when (val result = access.markAsFavorite(
                123654,
                "FakeRemoteServer.sampleSessionId",
                mediaType,
                "invalid api key"
            )) {
                is Result.Success -> {
                    fail("add tv show to watch list with valid api key result success` test failed")
                }

                is Result.Loading -> {
                }// Do nothing just wait for the result

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