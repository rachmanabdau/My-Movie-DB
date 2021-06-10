package com.example.mymoviddb.core.mock

import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.datasource.remote.moshi
import com.example.mymoviddb.core.model.*
import com.example.mymoviddb.core.model.category.movie.*
import com.example.mymoviddb.core.model.category.tv.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response


class FakeRemoteServer : NetworkService {

    private val responseFactory = FakeDataFactory()

    override fun getRequestTokenAsync(apiKey: String): Deferred<Response<RequestTokenModel?>> {
        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    responseFactory.getLoginSuccessResponse()
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun loginAsGuestAsync(apiKey: String): Deferred<Response<GuestSessionModel?>> {

        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(responseFactory.getGuestSessionSuccessResponse())
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun loginAsync(
        username: String,
        password: String,
        requestToken: String,
        apiKey: String
    ): Deferred<Response<LoginTokenModel?>> {
        val fakeUsername = "rachmanabdau"
        val fakePassword = "123456"
        val fakeRequestToken = "ff5c7eeb5a8870efe3cd7fc5c282cffd26800ecd"
        val realApiKey = BuildConfig.V3_AUTH


        return if (apiKey == realApiKey && username == fakeUsername &&
            password == fakePassword && requestToken == fakeRequestToken
        ) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    LoginTokenModel(
                        true, "", ""
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidAuthUserReposonse()
                )
            )
        }
    }

    override fun createSessionAsync(
        requestToken: String,
        apiKey: String
    ): Deferred<Response<NewSessionModel>> {

        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(responseFactory.getNewSessionSuccessResponse())
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun getPopularMoviesAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<PopularMovie>> {
        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseFactory.getPopularMovieResponse(page)))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun getNowPlayingMoviesAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<NowPlayingMovie>> {
        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseFactory.getNowPlayingMovieResponse(page)))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun getPopularTvShowAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<PopularTvShow>> {
        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseFactory.getPopularTvShow(page)))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun getOnAirTvShowAsync(page: Int, apiKey: String): Deferred<Response<OnAirTvShow>> {
        val realApiKey = BuildConfig.V3_AUTH

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseFactory.getOnairTvShow(page)))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidApiKeyReposonse()
                )
            )
        }
    }

    override fun searchMoviesAsync(
        title: String,
        page: Int,
        apiKey: String
    ): Deferred<Response<SearchMovieResult>> {
        val realApiKey = BuildConfig.V3_AUTH

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && title.isNotBlank() -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getSearchMovieResult(page)))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && title.isBlank() -> {
                val emptyMovies = SearchMovieResult(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun searchTvShowsAsync(
        title: String,
        page: Int,
        apiKey: String
    ): Deferred<Response<SearchTvResult>> {
        val realApiKey = BuildConfig.V3_AUTH

        return when {
            apiKey == realApiKey && title.isNotBlank() -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getSearchTvResult()))

            }
            apiKey == realApiKey && title.isBlank() -> {
                val emptyMovies = SearchTvResult(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getDetailhMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<MovieDetail>> {
        val realApiKey = BuildConfig.V3_AUTH

        return when {
            apiKey == realApiKey && movieId == 741067L -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getDetailMovieResponse()))

            }
            apiKey == realApiKey && movieId != 741067L -> {
                // Response Error 404: Data Not found
                CompletableDeferred(
                    Response.error(
                        404, responseFactory.getDetailNotFoundResponse()
                    )
                )

            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getDetailTvShowsAsync(tvId: Long, apiKey: String): Deferred<Response<TVDetail>> {
        val realApiKey = BuildConfig.V3_AUTH

        return when {
            apiKey == realApiKey && tvId == 62286L -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getDetailTvResponse()))

            }
            apiKey == realApiKey && tvId != 62286L -> {
                // Response Error 404: Data Not found
                CompletableDeferred(
                    Response.error(
                        404, responseFactory.getDetailNotFoundResponse()
                    )
                )
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getRecommendationMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<RecommendationMovie>> {

        val realApiKey = BuildConfig.V3_AUTH

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && movieId == 741067L -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getRecommendationMovie()))
            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && movieId != 741067L -> {
                val emptyMovies = RecommendationMovie(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getSimilarMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<SimilarMovie>> {


        val realApiKey = BuildConfig.V3_AUTH

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && movieId == 741067L -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getSimilarMovie()))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && movieId != 741067L -> {
                val emptyMovies = SimilarMovie(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getRecommendationTVShowsAsync(
        tvId: Long,
        apiKey: String
    ): Deferred<Response<RecommendationTvShow>> {

        val realApiKey = BuildConfig.V3_AUTH

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && tvId == 62286L -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getRecommendationTv()))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && tvId != 62286L -> {
                val emptyMovies = RecommendationTvShow(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getSimilarTVShowsAsync(
        tvId: Long,
        apiKey: String
    ): Deferred<Response<SimilarTvShow>> {

        val realApiKey = BuildConfig.V3_AUTH

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && tvId == 62286L -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getSimilarTv()))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && tvId != 62286L -> {
                val emptyMovies = SimilarTvShow(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getInvalidApiKeyReposonse()
                    )
                )
            }
        }
    }

    override fun getAccountDetailAsync(
        sessionId: String,
        apiKey: String
    ): Deferred<Response<UserDetail>> {

        val realApiKey = BuildConfig.V3_AUTH

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && sessionId == FakeDataFactory.sampleSessionId -> {
                // Response Success
                CompletableDeferred(Response.success(responseFactory.getUserDetail()))

            }
            else -> {
                // Response Error 401: invalid api key
                CompletableDeferred(
                    Response.error(
                        401, responseFactory.getAuthenticationError()
                    )
                )
            }
        }
    }

    override fun logoutAsync(
        userSessionId: Map<String, String>,
        apiKey: String
    ): Deferred<Response<ResponsedBackend>> {
        val logoutSuccessResponse = """{"success": true}"""
        val jsonConverter = moshi.adapter(ResponsedBackend::class.java)
        val responseSuccess = jsonConverter.fromJson(logoutSuccessResponse) as ResponsedBackend

        return if (apiKey == BuildConfig.V3_AUTH && userSessionId["session_id"] == FakeDataFactory.sampleSessionId) {
            CompletableDeferred(Response.success(responseSuccess))
        } else {
            // Response Error 401: invalid api key
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getLogout401Error()
                )
            )
        }
    }

    override fun getMovieAuthStateAsync(
        movieId: Long,
        sessionId: String,
        apiKey: String
    ): Deferred<Response<MediaState>> {
        val movieStateResponse = """{
  "id": 741067,
  "favorite": true,
  "rated": false,
  "watchlist": true
}"""
        val error401Response = """{
  "success": false,
  "status_code": 3,
  "status_message": "Authentication failed: You do not have permissions to access the service."
}"""
        val jsonConverter = moshi.adapter(MediaState::class.java)
        val responseSuccess = jsonConverter.fromJson(movieStateResponse) as MediaState

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId && movieId == 741067L) {
            CompletableDeferred(Response.success(responseSuccess))
        } else {
            // Response Error 401: invalid api key
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getTVAuthStateAsync(
        tvId: Long,
        sessionId: String,
        apiKey: String
    ): Deferred<Response<MediaState>> {
        val movieStateResponse = """{
  "id": 741067,
  "favorite": true,
  "rated": false,
  "watchlist": true
}"""
        val error401Response = """{
  "success": false,
  "status_code": 3,
  "status_message": "Authentication failed: You do not have permissions to access the service."
}"""
        val jsonConverter = moshi.adapter(MediaState::class.java)
        val responseSuccess = jsonConverter.fromJson(movieStateResponse) as MediaState

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId && tvId == 62286L) {
            CompletableDeferred(Response.success(responseSuccess))
        } else {
            // Response Error 401: invalid api key
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun markAsFavoriteAsync(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String,
        contentType: String
    ): Deferred<Response<ResponsedBackend>> {
        val addToFavouriteSuccessResponse = """{
  "success": true,
  "status_code": 12,
  "status_message": "The item/record was updated successfully."
}"""
        val removeFromFavouriteSuccessResponse = """{
  "success": true,
  "status_code": 13,
  "status_message": "The item/record was removed successfully."
}"""

        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""
        val jsonConverter = moshi.adapter(ResponsedBackend::class.java)
        val responseAddToFavouriteSuccess =
            jsonConverter.fromJson(addToFavouriteSuccessResponse) as ResponsedBackend
        val responseRemoveFromFavouriteSuccess =
            jsonConverter.fromJson(removeFromFavouriteSuccessResponse) as ResponsedBackend

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId && (sendMediaType.mediaType == "movie" || sendMediaType.mediaType == "tv")) {
            if (sendMediaType.favorite == true)
                CompletableDeferred(Response.success(responseAddToFavouriteSuccess))
            else CompletableDeferred(Response.success(responseRemoveFromFavouriteSuccess))
        } else {
            // Response Error 401: invalid api key
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getFavoriteMoviesAsync(
        accountId: Int,
        sessionId: String,
        page: Int,
        sortBy: String,
        apiKey: String
    ): Deferred<Response<FavouriteMovie>> {

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId
            && page == 1) {
            CompletableDeferred(Response.success(responseFactory.getFavouriteMovie(page)))
        } else {
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidUserShow()
                )
            )
        }
    }

    override fun getFavoriteTvShowAsync(
        accountId: Int,
        sessionId: String,
        page: Int,
        sortBy: String,
        apiKey: String
    ): Deferred<Response<FavouriteTvShow>> {

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId) {
            CompletableDeferred(Response.success(responseFactory.getFavouriteTv(page)))
        } else {
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidUserShow()
                )
            )
        }
    }

    override fun addToWatchListAsync(
        accoundId: Int,
        sessionId: String,
        sendMediaType: MarkMediaAs,
        apiKey: String,
        contentType: String
    ): Deferred<Response<ResponsedBackend>> {
        val addToWatchlistSuccessResponse = """{
  "success": true,
  "status_code": 12,
  "status_message": "The item/record was updated successfully."
}"""
        val removeFromWatchlistSuccessResponse = """{
  "success": true,
  "status_code": 13,
  "status_message": "The item/record was removed successfully."
}"""
        val jsonConverter = moshi.adapter(ResponsedBackend::class.java)
        val responseAddToWatchListSuccess =
            jsonConverter.fromJson(addToWatchlistSuccessResponse) as ResponsedBackend
        val responseRemoveFromWatchListSuccess =
            jsonConverter.fromJson(removeFromWatchlistSuccessResponse) as ResponsedBackend

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId && (sendMediaType.mediaType == "movie" || sendMediaType.mediaType == "tv")) {
            if (sendMediaType.watchList == true) CompletableDeferred(
                Response.success(responseAddToWatchListSuccess)
            )
            else CompletableDeferred(Response.success(responseRemoveFromWatchListSuccess))
        } else {
            // Response Error 401: invalid api key
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidUserShow()
                )
            )
        }
    }

    override fun getWatchListMoviesAsync(
        accountId: Int,
        sessionId: String,
        page: Int,
        sortBy: String,
        apiKey: String
    ): Deferred<Response<WatchListMovie>> {

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId) {
            CompletableDeferred(Response.success(responseFactory.getWatchListMovie(page)))
        } else {
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidUserShow()
                )
            )
        }
    }

    override fun getWatchListTvShowsAsync(
        accountId: Int,
        sessionId: String,
        page: Int,
        sortBy: String,
        apiKey: String
    ): Deferred<Response<WatchListTvShow>> {

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == FakeDataFactory.sampleSessionId) {
            CompletableDeferred(Response.success(responseFactory.getWatchListTv()))
        } else {
            CompletableDeferred(
                Response.error(
                    401, responseFactory.getInvalidUserShow()
                )
            )
        }
    }
}