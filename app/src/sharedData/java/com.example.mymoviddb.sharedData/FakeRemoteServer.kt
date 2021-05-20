package com.example.mymoviddb.sharedData

import com.example.mymoviddb.BuildConfig
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

    override fun getRequestTokenAsync(apiKey: String): Deferred<Response<RequestTokenModel?>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    RequestTokenModel(
                        "2016-08-26 17:04:39 UTC", "ff5c7eeb5a8870efe3cd7fc5c282cffd26800ecd", true
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun loginAsGuestAsync(apiKey: String): Deferred<Response<GuestSessionModel?>> {

        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    GuestSessionModel(
                        "2016-08-27 16:26:40 UT", "1ce82ec1223641636ad4a60b07de3581", true
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
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
        val errorResponse = """{
  "status_message": "Invalid username and/or password: You did not provide a valid login.",
  "success": false,
  "status_code": 7
}"""

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
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun createSessionAsync(
        requestToken: String,
        apiKey: String
    ): Deferred<Response<NewSessionModel>> {

        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(
                Response.success(
                    NewSessionModel(
                        "1ce82ec1223641636ad4a60b07de3581", true
                    )
                )
            )
        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getPopularMoviesAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<PreviewMovie>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val responseSuccess = jsonConverter.fromJson(popularMovieResponse) as PreviewMovie

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getNowPlayingMoviesAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<NowPlayingMovie>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val responseSuccess = jsonConverter.fromJson(nowPlayingMoviesResponse) as PreviewMovie

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getPopularTvShowAsync(
        page: Int,
        apiKey: String
    ): Deferred<Response<PopularTvShow>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val responseSuccess = jsonConverter.fromJson(popularTvResponse) as PreviewTvShow

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    override fun getOnAirTvShowAsync(page: Int, apiKey: String): Deferred<Response<OnAirTvShow>> {
        val realApiKey = BuildConfig.V3_AUTH
        val errorResponse = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val responseSuccess = jsonConverter.fromJson(onAirTVResponse) as PreviewTvShow

        return if (apiKey == realApiKey) {
            // Response Success
            CompletableDeferred(Response.success(responseSuccess))

        } else {
            // Response Error
            CompletableDeferred(
                Response.error(
                    401,
                    errorResponse
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
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
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val responseSuccess = jsonConverter.fromJson(popularMovieResponse) as PreviewMovie

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && title.isNotBlank() -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && title.isBlank() -> {
                val emptyMovies = PreviewMovie(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
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
    }

    override fun searchTvShowsAsync(
        title: String,
        page: Int,
        apiKey: String
    ): Deferred<Response<SearchTvResult>> {
        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val responseSuccess = jsonConverter.fromJson(popularTvResponse) as PreviewTvShow

        return when {
            apiKey == realApiKey && title.isNotBlank() -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            apiKey == realApiKey && title.isBlank() -> {
                val emptyMovies = PreviewTvShow(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
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
    }

    override fun getDetailhMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<MovieDetail>> {
        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val error404Response = """{
  "success": false,
  "status_code": 34,
  "status_message": "The resource you requested could not be found."
}"""
        val jsonConverter = moshi.adapter(MovieDetail::class.java)
        val responseSuccess = jsonConverter.fromJson(detailMovieResponse) as MovieDetail

        return when {
            apiKey == realApiKey && movieId == 741067L -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            apiKey == realApiKey && movieId != 741067L -> {
                // Response Error 404: Data Not found
                CompletableDeferred(
                    Response.error(
                        404,
                        error404Response
                            .toResponseBody("application/json;charset=utf-8".toMediaType())
                    )
                )

            }
            else -> {
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
    }

    override fun getDetailTvShowsAsync(tvId: Long, apiKey: String): Deferred<Response<TVDetail>> {
        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val error404Response = """{
  "success": false,
  "status_code": 34,
  "status_message": "The resource you requested could not be found."
}"""
        val jsonConverter = moshi.adapter(TVDetail::class.java)
        val responseSuccess = jsonConverter.fromJson(detailTVShowResponse) as TVDetail

        return when {
            apiKey == realApiKey && tvId == 62286L -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            apiKey == realApiKey && tvId != 62286L -> {
                // Response Error 404: Data Not found
                CompletableDeferred(
                    Response.error(
                        404,
                        error404Response
                            .toResponseBody("application/json;charset=utf-8".toMediaType())
                    )
                )
            }
            else -> {
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
    }

    override fun getRecommendationMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<RecommendationMovie>> {

        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val responseSuccess = jsonConverter.fromJson(recommendationMoviesResponse) as PreviewMovie

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && movieId == 741067L -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && movieId != 741067L -> {
                val emptyMovies = PreviewMovie(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
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
    }

    override fun getSimilarMoviesAsync(
        movieId: Long,
        apiKey: String
    ): Deferred<Response<SimilarMovie>> {


        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val responseSuccess = jsonConverter.fromJson(similarMoviesResponse) as PreviewMovie

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && movieId == 741067L -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && movieId != 741067L -> {
                val emptyMovies = PreviewMovie(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
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
    }

    override fun getRecommendationTVShowsAsync(
        tvId: Long,
        apiKey: String
    ): Deferred<Response<RecommendationTvShow>> {

        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val responseSuccess = jsonConverter.fromJson(recommendationTVShowsResponse) as PreviewTvShow

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && tvId == 62286L -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && tvId != 62286L -> {
                val emptyMovies = PreviewTvShow(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
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
    }

    override fun getSimilarTVShowsAsync(
        tvId: Long,
        apiKey: String
    ): Deferred<Response<SimilarTvShow>> {

        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "status_message": "Invalid API key: You must be granted a valid key.",
  "success": false,
  "status_code": 7
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val responseSuccess = jsonConverter.fromJson(similarTVShowsResponse) as PreviewTvShow

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && tvId == 62286L -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            // api key is valid and title is blank return success with empty list
            apiKey == realApiKey && tvId != 62286L -> {
                val emptyMovies = PreviewTvShow(
                    page = 1, totalResults = 0, totalPages = 0,
                    results = emptyList()
                )
                CompletableDeferred(Response.success(emptyMovies))
            }
            else -> {
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
    }

    override fun getAccountDetailAsync(
        sessionId: String,
        apiKey: String
    ): Deferred<Response<UserDetail>> {

        val realApiKey = BuildConfig.V3_AUTH
        val error401Response = """{
  "success": false,
  "status_code": 3,
  "status_message": "Authentication failed: You do not have permissions to access the service."
}"""
        val jsonConverter = moshi.adapter(UserDetail::class.java)
        val responseSuccess = jsonConverter.fromJson(accountDetailResponse) as UserDetail

        return when {
            // api key is valid and title is not blank return response success
            apiKey == realApiKey && sessionId == sampleSessionId -> {
                // Response Success
                CompletableDeferred(Response.success(responseSuccess))

            }
            else -> {
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
    }

    override fun logoutAsync(
        userSessionId: Map<String, String>,
        apiKey: String
    ): Deferred<Response<ResponsedBackend>> {
        val logoutSuccessResponse = """{"success": true}"""
        val jsonConverter = moshi.adapter(ResponsedBackend::class.java)
        val responseSuccess = jsonConverter.fromJson(logoutSuccessResponse) as ResponsedBackend

        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""

        return if (apiKey == BuildConfig.V3_AUTH && userSessionId["session_id"] == sampleSessionId) {
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

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId && movieId == 741067L) {
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

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId && tvId == 62286L) {
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

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId && (sendMediaType.mediaType == "movie" || sendMediaType.mediaType == "tv")) {
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
    ): Deferred<Response<PreviewMovie>> {
        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val favouriteMovieSuccess =
            jsonConverter.fromJson(favouriteMovies) as PreviewMovie

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId) {
            CompletableDeferred(Response.success(favouriteMovieSuccess))
        } else {
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
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
        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val favouriteTVSuccess =
            jsonConverter.fromJson(favouriteTVShows) as PreviewTvShow

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId) {
            CompletableDeferred(Response.success(favouriteTVSuccess))
        } else {
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
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

        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""
        val jsonConverter = moshi.adapter(ResponsedBackend::class.java)
        val responseAddToWatchListSuccess =
            jsonConverter.fromJson(addToWatchlistSuccessResponse) as ResponsedBackend
        val responseRemoveFromWatchListSuccess =
            jsonConverter.fromJson(removeFromWatchlistSuccessResponse) as ResponsedBackend

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId && (sendMediaType.mediaType == "movie" || sendMediaType.mediaType == "tv")) {
            if (sendMediaType.watchList == true) CompletableDeferred(
                Response.success(responseAddToWatchListSuccess)
            )
            else CompletableDeferred(Response.success(responseRemoveFromWatchListSuccess))
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

    override fun getWatchListMoviesAsync(
        accountId: Int,
        sessionId: String,
        page: Int,
        sortBy: String,
        apiKey: String
    ): Deferred<Response<WatchListMovie>> {
        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""
        val jsonConverter = moshi.adapter(PreviewMovie::class.java)
        val watchListMovieSuccess =
            jsonConverter.fromJson(watchListMovies) as PreviewMovie

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId) {
            CompletableDeferred(Response.success(watchListMovieSuccess))
        } else {
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
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
        val error401Response = """{
  "success": false,
  "status_code": 6,
  "status_message": "Invalid id: The pre-requisite id is invalid or not found."
}"""
        val jsonConverter = moshi.adapter(PreviewTvShow::class.java)
        val watchListTVSuccess =
            jsonConverter.fromJson(watchListTV) as PreviewTvShow

        return if (apiKey == BuildConfig.V3_AUTH && sessionId == sampleSessionId) {
            CompletableDeferred(Response.success(watchListTVSuccess))
        } else {
            CompletableDeferred(
                Response.error(
                    401,
                    error401Response
                        .toResponseBody("application/json;charset=utf-8".toMediaType())
                )
            )
        }
    }

    companion object {

        const val detailMovieResponse = """{
  "adult": false,
  "backdrop_path": "/mc48QVtMhohMFrHGca8OHTB6C2B.jpg",
  "belongs_to_collection": null,
  "budget": 0,
  "genres": [
    {
      "id": 28,
      "name": "Action"
    },
    {
      "id": 53,
      "name": "Thriller"
    },
    {
      "id": 12,
      "name": "Adventure"
    },
    {
      "id": 18,
      "name": "Drama"
    }
  ],
  "homepage": "https://xmovies8.app/",
  "id": 741067,
  "imdb_id": "tt10804786",
  "original_language": "en",
  "original_title": "Welcome to Sudden Death",
  "overview": "Jesse Freeman is a former special forces officer and explosives expert now working a regular job as a security guard in a state-of-the-art basketball arena. Trouble erupts when a tech-savvy cadre of terrorists kidnap the team's owner and Jesse's daughter during opening night. Facing a ticking clock and impossible odds, it's up to Jesse to not only save them but also a full house of fans in this highly charged action thriller.",
  "popularity": 851.543,
  "poster_path": "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
  "production_companies": [],
  "production_countries": [
    {
      "iso_3166_1": "CA",
      "name": "Canada"
    },
    {
      "iso_3166_1": "FR",
      "name": "France"
    },
    {
      "iso_3166_1": "JP",
      "name": "Japan"
    },
    {
      "iso_3166_1": "GB",
      "name": "United Kingdom"
    },
    {
      "iso_3166_1": "US",
      "name": "United States of America"
    }
  ],
  "release_date": "2020-09-29",
  "revenue": 0,
  "runtime": 80,
  "spoken_languages": [
    {
      "iso_639_1": "en",
      "name": "English"
    },
    {
      "iso_639_1": "de",
      "name": "Deutsch"
    }
  ],
  "status": "Released",
  "tagline": "",
  "title": "Welcome to Sudden Death",
  "video": false,
  "vote_average": 6.3,
  "vote_count": 157
}"""

        const val popularMovieResponse = """{
  "page": 1,
  "total_results": 10000,
  "total_pages": 500,
  "results": [
    {
      "popularity": 2367.963,
      "vote_count": 28,
      "video": false,
      "poster_path": "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
      "id": 724989,
      "adult": false,
      "backdrop_path": "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
      "original_language": "en",
      "original_title": "Hard Kill",
      "genre_ids": [
        28,
        53
      ],
      "title": "Hard Kill",
      "vote_average": 4.2,
      "overview": "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 1815.523,
      "vote_count": 201,
      "video": false,
      "poster_path": "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
      "id": 528085,
      "adult": false,
      "backdrop_path": "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
      "original_language": "en",
      "original_title": "2067",
      "genre_ids": [
        18,
        878,
        53
      ],
      "title": "2067",
      "vote_average": 5.4,
      "overview": "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
      "release_date": "2020-10-01"
    },
    {
      "popularity": 1591.383,
      "vote_count": 305,
      "video": false,
      "poster_path": "/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
      "id": 531219,
      "adult": false,
      "backdrop_path": "/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
      "original_language": "en",
      "original_title": "Roald Dahl's The Witches",
      "genre_ids": [
        14,
        10751,
        12,
        35,
        27
      ],
      "title": "Roald Dahl's The Witches",
      "vote_average": 7.3,
      "overview": "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
      "release_date": "2020-10-26"
    },
    {
      "popularity": 1109.5,
      "vote_count": 20,
      "video": false,
      "poster_path": "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
      "id": 635302,
      "adult": false,
      "backdrop_path": "/xoqr4dMbRJnzuhsWDF3XNHQwJ9x.jpg",
      "original_language": "ja",
      "original_title": "劇場版「鬼滅の刃」無限列車編",
      "genre_ids": [
        16,
        28,
        36,
        12,
        14,
        18
      ],
      "title": "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
      "vote_average": 7.5,
      "overview": "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 1070.875,
      "vote_count": 2306,
      "video": false,
      "poster_path": "/riYInlsq2kf1AWoGm80JQW5dLKp.jpg",
      "id": 497582,
      "adult": false,
      "backdrop_path": "/kMe4TKMDNXTKptQPAdOF0oZHq3V.jpg",
      "original_language": "en",
      "original_title": "Enola Holmes",
      "genre_ids": [
        80,
        18,
        9648
      ],
      "title": "Enola Holmes",
      "vote_average": 7.6,
      "overview": "While searching for her missing mother, intrepid teen Enola Holmes uses her sleuthing skills to outsmart big brother Sherlock and help a runaway lord.",
      "release_date": "2020-09-23"
    },
    {
      "popularity": 1064.722,
      "vote_count": 128,
      "video": false,
      "poster_path": "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
      "id": 741067,
      "adult": false,
      "backdrop_path": "/aO5ILS7qnqtFIprbJ40zla0jhpu.jpg",
      "original_language": "en",
      "original_title": "Welcome to Sudden Death",
      "genre_ids": [
        28,
        12,
        18,
        53
      ],
      "title": "Welcome to Sudden Death",
      "vote_average": 6.4,
      "overview": "Jesse Freeman is a former special forces officer and explosives expert now working a regular job as a security guard in a state-of-the-art basketball arena. Trouble erupts when a tech-savvy cadre of terrorists kidnap the team's owner and Jesse's daughter during opening night. Facing a ticking clock and impossible odds, it's up to Jesse to not only save them but also a full house of fans in this highly charged action thriller.",
      "release_date": "2020-09-29"
    },
    {
      "popularity": 976.711,
      "vote_count": 2,
      "video": false,
      "poster_path": "/eHFJ15cZ1pfk60XTcTl9GYxnhf6.jpg",
      "id": 733278,
      "adult": false,
      "backdrop_path": "/bMrYAvAE5MalHKglxAKduqV5S4k.jpg",
      "original_language": "en",
      "original_title": "Picture Perfect Mysteries: Exit Stage Death",
      "genre_ids": [],
      "title": "Picture Perfect Mysteries: Exit Stage Death",
      "vote_average": 6,
      "overview": "While backstage on opening night of a new play by celebrated murder mystery author/director Neil Khan, photographer Allie Adams discovers the body of the show’s leading lady, murdered before the curtain went up. As Willow Haven PD Detective Sam Acosta launches his investigation, he invites Allie – who has been helpful in solving his first two murder cases since joining the force -- to unofficially assist him on the. case. However, Allie’s involvement makes her a target for murder herself when she gets too close to the truth.",
      "release_date": "2020-10-11"
    },
    {
      "popularity": 902.112,
      "vote_count": 205,
      "video": false,
      "poster_path": "/r4Lm1XKP0VsTgHX4LG4syAwYA2I.jpg",
      "id": 590223,
      "adult": false,
      "backdrop_path": "/lA5fOBqTOQBQ1s9lEYYPmNXoYLi.jpg",
      "original_language": "en",
      "original_title": "Love and Monsters",
      "genre_ids": [
        28,
        12,
        35,
        878
      ],
      "title": "Love and Monsters",
      "vote_average": 7.7,
      "overview": "Seven years after the Monsterpocalypse, Joel Dawson, along with the rest of humanity, has been living underground ever since giant creatures took control of the land. After reconnecting over radio with his high school girlfriend Aimee, who is now 80 miles away at a coastal colony, Joel begins to fall for her again. As Joel realizes that there’s nothing left for him underground, he decides against all logic to venture out to Aimee, despite all the dangerous monsters that stand in his way.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 835.74,
      "vote_count": 500,
      "video": false,
      "poster_path": "/6agKYU5IQFpuDyUYPu39w7UCRrJ.jpg",
      "id": 740985,
      "adult": false,
      "backdrop_path": "/hbrXbVoE0NuA1ORoSGGYNASagrl.jpg",
      "original_language": "en",
      "original_title": "Borat Subsequent Moviefilm",
      "genre_ids": [
        35
      ],
      "title": "Borat Subsequent Moviefilm",
      "vote_average": 6.8,
      "overview": "14 years after making a film about his journey across the USA, Borat risks life and limb when he returns to the United States with his young daughter, and reveals more about the culture, the COVID-19 pandemic, and the political elections.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 651.132,
      "vote_count": 326,
      "video": false,
      "poster_path": "/uOw5JD8IlD546feZ6oxbIjvN66P.jpg",
      "id": 718444,
      "adult": false,
      "backdrop_path": "/x4UkhIQuHIJyeeOTdcbZ3t3gBSa.jpg",
      "original_language": "en",
      "original_title": "Rogue",
      "genre_ids": [
        28,
        12,
        18,
        53
      ],
      "title": "Rogue",
      "vote_average": 5.8,
      "overview": "Battle-hardened O’Hara leads a lively mercenary team of soldiers on a daring mission: rescue hostages from their captors in remote Africa. But as the mission goes awry and the team is stranded, O’Hara’s squad must face a bloody, brutal encounter with a gang of rebels.",
      "release_date": "2020-08-20"
    },
    {
      "popularity": 638.944,
      "vote_count": 156,
      "video": false,
      "poster_path": "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
      "id": 694919,
      "adult": false,
      "backdrop_path": "/pq0JSpwyT2URytdFG0euztQPAyR.jpg",
      "original_language": "en",
      "original_title": "Money Plane",
      "genre_ids": [
        28
      ],
      "title": "Money Plane",
      "vote_average": 5.9,
      "overview": "A professional thief with ${'$'}40 million in debt and his family's life on the line must commit one final heist - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
      "release_date": "2020-09-29"
    },
    {
      "popularity": 631.045,
      "vote_count": 1054,
      "video": false,
      "poster_path": "/kiX7UYfOpYrMFSAGbI6j1pFkLzQ.jpg",
      "id": 613504,
      "adult": false,
      "backdrop_path": "/6hgItrYQEG33y0I7yP2SRl2ei4w.jpg",
      "original_language": "en",
      "original_title": "After We Collided",
      "genre_ids": [
        18,
        10749
      ],
      "title": "After We Collided",
      "vote_average": 7.2,
      "overview": "Tessa finds herself struggling with her complicated relationship with Hardin; she faces a dilemma that could change their lives forever.",
      "release_date": "2020-09-02"
    },
    {
      "popularity": 624.425,
      "vote_count": 545,
      "video": false,
      "poster_path": "/qzA87Wf4jo1h8JMk9GilyIYvwsA.jpg",
      "id": 539885,
      "adult": false,
      "backdrop_path": "/54yOImQgj8i85u9hxxnaIQBRUuo.jpg",
      "original_language": "en",
      "original_title": "Ava",
      "genre_ids": [
        28,
        80,
        18,
        53
      ],
      "title": "Ava",
      "vote_average": 5.8,
      "overview": "A black ops assassin is forced to fight for her own survival after a job goes dangerously wrong.",
      "release_date": "2020-07-02"
    },
    {
      "popularity": 613.467,
      "vote_count": 2672,
      "video": false,
      "poster_path": "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
      "id": 337401,
      "adult": false,
      "backdrop_path": "/zzWGRw277MNoCs3zhyG3YmYQsXv.jpg",
      "original_language": "en",
      "original_title": "Mulan",
      "genre_ids": [
        28,
        12,
        18,
        14
      ],
      "title": "Mulan",
      "vote_average": 7.3,
      "overview": "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
      "release_date": "2020-09-04"
    },
    {
      "popularity": 607.834,
      "vote_count": 676,
      "video": false,
      "poster_path": "/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg",
      "id": 581392,
      "adult": false,
      "backdrop_path": "/2nFzxaAK7JIsk6l7qZ8rFBsa3yW.jpg",
      "original_language": "ko",
      "original_title": "반도",
      "genre_ids": [
        28,
        27,
        53
      ],
      "title": "Peninsula",
      "vote_average": 7,
      "overview": "A soldier and his team battle hordes of post-apocalyptic zombies in the wastelands of the Korean Peninsula.",
      "release_date": "2020-07-15"
    },
    {
      "popularity": 586.957,
      "vote_count": 78,
      "video": false,
      "poster_path": "/bkld8Me0WiLWipLORRNfF1yIPHu.jpg",
      "id": 624963,
      "adult": false,
      "backdrop_path": "/ezLKohe4HKsHQbwQwhv0ARo83NC.jpg",
      "original_language": "en",
      "original_title": "A Babysitter's Guide to Monster Hunting",
      "genre_ids": [
        12,
        35,
        14,
        10751
      ],
      "title": "A Babysitter's Guide to Monster Hunting",
      "vote_average": 6.1,
      "overview": "Recruited by a secret society of babysitters, a high schooler battles the Boogeyman and his monsters when they nab the boy she's watching on Halloween.",
      "release_date": "2020-10-14"
    },
    {
      "popularity": 559.61,
      "vote_count": 117,
      "video": false,
      "poster_path": "/xqvX5A24dbIWaeYsMTxxKX5qOfz.jpg",
      "id": 660982,
      "adult": false,
      "backdrop_path": "/75ooojtgiKYm5LcCczbCexioZze.jpg",
      "original_language": "en",
      "original_title": "American Pie Presents: Girls' Rules",
      "genre_ids": [
        35
      ],
      "title": "American Pie Presents: Girls Rules",
      "vote_average": 6.3,
      "overview": "It's Senior year at East Great Falls. Annie, Kayla, Michelle, and Stephanie decide to harness their girl power and band together to get what they want their last year of high school.",
      "release_date": "2020-10-06"
    },
    {
      "popularity": 549.968,
      "vote_count": 225,
      "video": false,
      "poster_path": "/vJHSParlylICnI7DuuI54nfTPRR.jpg",
      "id": 438396,
      "adult": false,
      "backdrop_path": "/qGZe9qTuydxyJYQ60XDtEckzLR8.jpg",
      "original_language": "es",
      "original_title": "Orígenes secretos",
      "genre_ids": [
        18,
        53
      ],
      "title": "Unknown Origins",
      "vote_average": 6.2,
      "overview": "In Madrid, Spain, a mysterious serial killer ruthlessly murders his victims by recreating the first appearance of several comic book superheroes. Cosme, a veteran police inspector who is about to retire, works on the case along with the tormented inspector David Valentín and his own son Jorge Elías, a nerdy young man who owns a comic book store.",
      "release_date": "2020-08-28"
    },
    {
      "popularity": 548.793,
      "vote_count": 903,
      "video": false,
      "poster_path": "/tI8ocADh22GtQFV28vGHaBZVb0U.jpg",
      "id": 475430,
      "adult": false,
      "backdrop_path": "/o0F8xAt8YuEm5mEZviX5pEFC12y.jpg",
      "original_language": "en",
      "original_title": "Artemis Fowl",
      "genre_ids": [
        28,
        12,
        14,
        878,
        10751
      ],
      "title": "Artemis Fowl",
      "vote_average": 5.8,
      "overview": "Artemis Fowl is a 12-year-old genius and descendant of a long line of criminal masterminds. He soon finds himself in an epic battle against a race of powerful underground fairies who may be behind his father's disappearance.",
      "release_date": "2020-06-12"
    },
    {
      "popularity": 546.091,
      "vote_count": 17,
      "video": false,
      "poster_path": "/z0r3YjyJSLqf6Hz0rbBAnEhNXQ7.jpg",
      "id": 697064,
      "adult": false,
      "backdrop_path": "/7WKIOXJa2JjHygE8Yta3uaCv6GC.jpg",
      "original_language": "en",
      "original_title": "Beckman",
      "genre_ids": [
        28
      ],
      "title": "Beckman",
      "vote_average": 4.8,
      "overview": "A contract killer, becomes the reverend of a LA church, until a cult leader and his minions kidnap his daughter. Blinded by vengeance, he cuts a bloody path across the city. The only thing that can stop him is his newfound faith.",
      "release_date": "2020-09-10"
    }
  ]
}"""

        const val nowPlayingMoviesResponse = """{
  "results": [
    {
      "popularity": 2367.963,
      "vote_count": 28,
      "video": false,
      "poster_path": "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
      "id": 724989,
      "adult": false,
      "backdrop_path": "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
      "original_language": "en",
      "original_title": "Hard Kill",
      "genre_ids": [
        28,
        53
      ],
      "title": "Hard Kill",
      "vote_average": 4.2,
      "overview": "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 1815.523,
      "vote_count": 201,
      "video": false,
      "poster_path": "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
      "id": 528085,
      "adult": false,
      "backdrop_path": "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
      "original_language": "en",
      "original_title": "2067",
      "genre_ids": [
        18,
        878,
        53
      ],
      "title": "2067",
      "vote_average": 5.4,
      "overview": "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
      "release_date": "2020-10-01"
    },
    {
      "popularity": 1591.383,
      "vote_count": 305,
      "video": false,
      "poster_path": "/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
      "id": 531219,
      "adult": false,
      "backdrop_path": "/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
      "original_language": "en",
      "original_title": "Roald Dahl's The Witches",
      "genre_ids": [
        14,
        10751,
        12,
        35,
        27
      ],
      "title": "Roald Dahl's The Witches",
      "vote_average": 7.3,
      "overview": "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
      "release_date": "2020-10-26"
    },
    {
      "popularity": 1109.5,
      "vote_count": 20,
      "video": false,
      "poster_path": "/h8Rb9gBr48ODIwYUttZNYeMWeUU.jpg",
      "id": 635302,
      "adult": false,
      "backdrop_path": "/xoqr4dMbRJnzuhsWDF3XNHQwJ9x.jpg",
      "original_language": "ja",
      "original_title": "劇場版「鬼滅の刃」無限列車編",
      "genre_ids": [
        16,
        28,
        36,
        12,
        14,
        18
      ],
      "title": "Demon Slayer: Kimetsu no Yaiba - The Movie: Mugen Train",
      "vote_average": 7.5,
      "overview": "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 902.112,
      "vote_count": 205,
      "video": false,
      "poster_path": "/r4Lm1XKP0VsTgHX4LG4syAwYA2I.jpg",
      "id": 590223,
      "adult": false,
      "backdrop_path": "/lA5fOBqTOQBQ1s9lEYYPmNXoYLi.jpg",
      "original_language": "en",
      "original_title": "Love and Monsters",
      "genre_ids": [
        28,
        12,
        35,
        878
      ],
      "title": "Love and Monsters",
      "vote_average": 7.7,
      "overview": "Seven years after the Monsterpocalypse, Joel Dawson, along with the rest of humanity, has been living underground ever since giant creatures took control of the land. After reconnecting over radio with his high school girlfriend Aimee, who is now 80 miles away at a coastal colony, Joel begins to fall for her again. As Joel realizes that there’s nothing left for him underground, he decides against all logic to venture out to Aimee, despite all the dangerous monsters that stand in his way.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 651.132,
      "vote_count": 326,
      "video": false,
      "poster_path": "/uOw5JD8IlD546feZ6oxbIjvN66P.jpg",
      "id": 718444,
      "adult": false,
      "backdrop_path": "/x4UkhIQuHIJyeeOTdcbZ3t3gBSa.jpg",
      "original_language": "en",
      "original_title": "Rogue",
      "genre_ids": [
        28,
        12,
        18,
        53
      ],
      "title": "Rogue",
      "vote_average": 5.8,
      "overview": "Battle-hardened O’Hara leads a lively mercenary team of soldiers on a daring mission: rescue hostages from their captors in remote Africa. But as the mission goes awry and the team is stranded, O’Hara’s squad must face a bloody, brutal encounter with a gang of rebels.",
      "release_date": "2020-08-20"
    },
    {
      "popularity": 638.944,
      "vote_count": 156,
      "video": false,
      "poster_path": "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
      "id": 694919,
      "adult": false,
      "backdrop_path": "/pq0JSpwyT2URytdFG0euztQPAyR.jpg",
      "original_language": "en",
      "original_title": "Money Plane",
      "genre_ids": [
        28
      ],
      "title": "Money Plane",
      "vote_average": 5.9,
      "overview": "A professional thief with ${'$'}40 million in debt and his family's life on the line must commit one final heist - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
      "release_date": "2020-09-29"
    },
    {
      "popularity": 631.045,
      "vote_count": 1054,
      "video": false,
      "poster_path": "/kiX7UYfOpYrMFSAGbI6j1pFkLzQ.jpg",
      "id": 613504,
      "adult": false,
      "backdrop_path": "/6hgItrYQEG33y0I7yP2SRl2ei4w.jpg",
      "original_language": "en",
      "original_title": "After We Collided",
      "genre_ids": [
        18,
        10749
      ],
      "title": "After We Collided",
      "vote_average": 7.2,
      "overview": "Tessa finds herself struggling with her complicated relationship with Hardin; she faces a dilemma that could change their lives forever.",
      "release_date": "2020-09-02"
    },
    {
      "popularity": 624.425,
      "vote_count": 545,
      "video": false,
      "poster_path": "/qzA87Wf4jo1h8JMk9GilyIYvwsA.jpg",
      "id": 539885,
      "adult": false,
      "backdrop_path": "/54yOImQgj8i85u9hxxnaIQBRUuo.jpg",
      "original_language": "en",
      "original_title": "Ava",
      "genre_ids": [
        28,
        80,
        18,
        53
      ],
      "title": "Ava",
      "vote_average": 5.8,
      "overview": "A black ops assassin is forced to fight for her own survival after a job goes dangerously wrong.",
      "release_date": "2020-07-02"
    },
    {
      "popularity": 613.467,
      "vote_count": 2672,
      "video": false,
      "poster_path": "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
      "id": 337401,
      "adult": false,
      "backdrop_path": "/zzWGRw277MNoCs3zhyG3YmYQsXv.jpg",
      "original_language": "en",
      "original_title": "Mulan",
      "genre_ids": [
        28,
        12,
        18,
        14
      ],
      "title": "Mulan",
      "vote_average": 7.3,
      "overview": "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
      "release_date": "2020-09-04"
    },
    {
      "popularity": 607.834,
      "vote_count": 676,
      "video": false,
      "poster_path": "/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg",
      "id": 581392,
      "adult": false,
      "backdrop_path": "/2nFzxaAK7JIsk6l7qZ8rFBsa3yW.jpg",
      "original_language": "ko",
      "original_title": "반도",
      "genre_ids": [
        28,
        27,
        53
      ],
      "title": "Peninsula",
      "vote_average": 7,
      "overview": "A soldier and his team battle hordes of post-apocalyptic zombies in the wastelands of the Korean Peninsula.",
      "release_date": "2020-07-15"
    },
    {
      "popularity": 454.521,
      "vote_count": 56,
      "video": false,
      "poster_path": "/n9OzZmPMnVrV0cMQ7amX0DtBkQH.jpg",
      "id": 509635,
      "adult": false,
      "backdrop_path": "/obExOU9qDnMcxvWPumoD14oxup5.jpg",
      "original_language": "en",
      "original_title": "Alone",
      "genre_ids": [
        53
      ],
      "title": "Alone",
      "vote_average": 6.2,
      "overview": "A recently widowed traveler is kidnapped by a cold blooded killer, only to escape into the wilderness where she is forced to battle against the elements as her pursuer closes in on her.",
      "release_date": "2020-09-10"
    },
    {
      "popularity": 400.351,
      "vote_count": 155,
      "video": false,
      "poster_path": "/lQfdytwN7eh0tXWjIiMceFdBBvD.jpg",
      "id": 560050,
      "adult": false,
      "backdrop_path": "/htBUhLSS7FfHtydgYxUWjL3J1Q1.jpg",
      "original_language": "en",
      "original_title": "Over the Moon",
      "genre_ids": [
        12,
        16,
        14,
        10751
      ],
      "title": "Over the Moon",
      "vote_average": 7.9,
      "overview": "A girl builds a rocket to travel to the moon in hopes of meeting the legendary Moon Goddess.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 293.355,
      "vote_count": 94,
      "video": false,
      "poster_path": "/ltyARDw2EFXZ2H2ERnlEctXPioP.jpg",
      "id": 425001,
      "adult": false,
      "backdrop_path": "/4gKyQ1McHa8ZKDsYoyKQSevF01J.jpg",
      "original_language": "en",
      "original_title": "The War with Grandpa",
      "genre_ids": [
        35,
        10751,
        18
      ],
      "title": "The War with Grandpa",
      "vote_average": 6.2,
      "overview": "Sixth-grader Peter is pretty much your average kid—he likes gaming, hanging with his friends and his beloved pair of Air Jordans. But when his recently widowed grandfather Ed  moves in with Peter’s family, the boy is forced to give up his most prized possession of all, his bedroom. Unwilling to let such an injustice stand, Peter devises a series of increasingly elaborate pranks to drive out the interloper, but Grandpa Ed won’t go without a fight.",
      "release_date": "2020-08-27"
    },
    {
      "popularity": 290.758,
      "vote_count": 2,
      "video": false,
      "poster_path": "/zamW7KUYYtAdxMtTuCzukRjHTPO.jpg",
      "id": 748224,
      "adult": false,
      "backdrop_path": "/flNp7NdzBC1UGctzuubQveuVSF5.jpg",
      "original_language": "en",
      "original_title": "Reaptown",
      "genre_ids": [
        27
      ],
      "title": "Reaptown",
      "vote_average": 6,
      "overview": "In this supernatural horror film, Carrie Baldwin is freed from prison under the conditions of a work-release program in Reaptown, Nevada. As she struggles to find her missing sister while working the night shift as a security guard for the Reaptown Railway Museum, Carrie soon finds herself in the presence of evil. Could the Railway hold the clues to her sister's mysterious disappearance?",
      "release_date": "2020-09-28"
    },
    {
      "popularity": 277.491,
      "vote_count": 1240,
      "video": false,
      "poster_path": "/7W0G3YECgDAfnuiHG91r8WqgIOe.jpg",
      "id": 446893,
      "adult": false,
      "backdrop_path": "/qsxhnirlp7y4Ae9bd11oYJSX59j.jpg",
      "original_language": "en",
      "original_title": "Trolls World Tour",
      "genre_ids": [
        12,
        16,
        35,
        14,
        10402,
        10751
      ],
      "title": "Trolls World Tour",
      "vote_average": 7.6,
      "overview": "Queen Poppy and Branch make a surprising discovery — there are other Troll worlds beyond their own, and their distinct differences create big clashes between these various tribes. When a mysterious threat puts all of the Trolls across the land in danger, Poppy, Branch, and their band of friends must embark on an epic quest to create harmony among the feuding Trolls to unite them against certain doom.",
      "release_date": "2020-03-12"
    },
    {
      "popularity": 265.031,
      "vote_count": 65,
      "video": false,
      "poster_path": "/zfdhsR3Y3xw42OHrMpi0oBw0Uk8.jpg",
      "id": 741074,
      "adult": false,
      "backdrop_path": "/DA7gzvlBoxMNL0XmGgTZOyv67P.jpg",
      "original_language": "en",
      "original_title": "Once Upon a Snowman",
      "genre_ids": [
        16,
        35,
        14,
        10751
      ],
      "title": "Once Upon a Snowman",
      "vote_average": 7.4,
      "overview": "The previously untold origins of Olaf, the innocent and insightful, summer-loving snowman are revealed as we follow Olaf’s first steps as he comes to life and searches for his identity in the snowy mountains outside Arendelle.",
      "release_date": "2020-10-23"
    },
    {
      "popularity": 259.417,
      "vote_count": 87,
      "video": false,
      "poster_path": "/4BgSWFMW2MJ0dT5metLzsRWO7IJ.jpg",
      "id": 726739,
      "adult": false,
      "backdrop_path": "/t22fWbzdnThPseipsdpwgdPOPCR.jpg",
      "original_language": "en",
      "original_title": "Cats & Dogs 3: Paws Unite",
      "genre_ids": [
        28,
        35
      ],
      "title": "Cats & Dogs 3: Paws Unite",
      "vote_average": 6.5,
      "overview": "It's been ten years since the creation of the Great Truce, an elaborate joint-species surveillance system designed and monitored by cats and dogs to keep the peace when conflicts arise. But when a tech-savvy villain hacks into wireless networks to use frequencies only heard by cats and dogs, he manipulates them into conflict and the worldwide battle between cats and dogs is BACK ON. Now, a team of inexperienced and untested agents will have to use their old-school animal instincts to restore order and peace between cats and dogs everywhere.",
      "release_date": "2020-10-02"
    },
    {
      "popularity": 232.215,
      "vote_count": 233,
      "video": false,
      "poster_path": "/bSKVKcCXdKxkbgf0LL8lBTPG02e.jpg",
      "id": 505379,
      "adult": false,
      "backdrop_path": "/rVkptIl9QkhpB5xM6BnUAHOJ43b.jpg",
      "original_language": "en",
      "original_title": "Rebecca",
      "genre_ids": [
        18,
        9648,
        53,
        10749
      ],
      "title": "Rebecca",
      "vote_average": 6.3,
      "overview": "After a whirlwind romance with a wealthy widower, a naïve bride moves to his family estate but can't escape the haunting shadow of his late wife.",
      "release_date": "2020-10-16"
    },
    {
      "popularity": 213.559,
      "vote_count": 409,
      "video": false,
      "poster_path": "/hPkqY2EMqWUnFEoedukilIUieVG.jpg",
      "id": 531876,
      "adult": false,
      "backdrop_path": "/n1RohH2VoK1CdVI2fXvcP19dSlm.jpg",
      "original_language": "en",
      "original_title": "The Outpost",
      "genre_ids": [
        28,
        18,
        36,
        10752
      ],
      "title": "The Outpost",
      "vote_average": 6.7,
      "overview": "A small unit of U.S. soldiers, alone at the remote Combat Outpost Keating, located deep in the valley of three mountains in Afghanistan, battles to defend against an overwhelming force of Taliban fighters in a coordinated attack. The Battle of Kamdesh, as it was known, was the bloodiest American engagement of the Afghan War in 2009 and Bravo Troop 3-61 CAV became one of the most decorated units of the 19-year conflict.",
      "release_date": "2020-06-24"
    }
  ],
  "page": 1,
  "total_results": 1492,
  "dates": {
    "maximum": "2020-11-04",
    "minimum": "2020-09-17"
  },
  "total_pages": 75
}"""

        const val recommendationMoviesResponse = """{
  "page": 1,
  "results": [
    {
      "id": 472815,
      "video": false,
      "vote_count": 5,
      "vote_average": 6,
      "title": "Buddy Games",
      "release_date": "2019-02-10",
      "original_language": "en",
      "original_title": "Buddy Games",
      "genre_ids": [
        35,
        18
      ],
      "backdrop_path": "/hDQEzfrFkQJg5qblG2Tv7ragth.jpg",
      "adult": false,
      "overview": "A group of friends reunite to play The Buddy Games, a wild assortment of absurd physical and mental challenges. In the process, they'll heal old wounds, right past wrongs and figure out the true meaning of friendship...or die trying.",
      "poster_path": "/qK56ahbY4382N0kKMIf1ypqf99j.jpg",
      "popularity": 18.773
    },
    {
      "id": 738256,
      "video": false,
      "vote_count": 4,
      "vote_average": 7.3,
      "title": "Magari resto",
      "release_date": "2020-09-10",
      "original_language": "it",
      "original_title": "Magari resto",
      "genre_ids": [
        35
      ],
      "backdrop_path": "/1JgQIwAVNVm0n3Ym5nPMt9eHHL4.jpg",
      "adult": false,
      "overview": "",
      "poster_path": "/c2BItOH2Dex6ExypexKTn6JjPpT.jpg",
      "popularity": 1.4
    },
    {
      "id": 755401,
      "video": false,
      "vote_count": 4,
      "vote_average": 7,
      "title": "Skydog",
      "release_date": "2020-10-20",
      "original_language": "en",
      "original_title": "Skydog",
      "genre_ids": [
        28,
        18,
        12,
        9648,
        53
      ],
      "backdrop_path": "/4mmrgZW9bcgMRFdV1lCcXqCEXEr.jpg",
      "adult": false,
      "overview": "After a high school senior working on his pilot’s license rescues a dog named Oreo, he finds out his mom is a CIA agent who’s been captured. He teams up with Oreo and a new friend to find his mother and uncover double agents inside the CIA.",
      "poster_path": "/dWtMI34vh1oloILjgO8qeE4jaxY.jpg",
      "popularity": 12.112
    },
    {
      "id": 706822,
      "video": false,
      "vote_count": 3,
      "vote_average": 6.3,
      "title": "La piazza della mia città - Bologna e Lo Stato Sociale",
      "release_date": "2020-09-17",
      "original_language": "it",
      "original_title": "La piazza della mia città - Bologna e Lo Stato Sociale",
      "genre_ids": [
        99
      ],
      "backdrop_path": "/6QxlkVkkC6pi47MtVT9tB670loU.jpg",
      "adult": false,
      "overview": "",
      "poster_path": "/joAuVNEFVKqyMOaMX61FCzNiOWy.jpg",
      "popularity": 1.093
    },
    {
      "id": 546121,
      "video": false,
      "vote_count": 6,
      "vote_average": 8.5,
      "title": "Run",
      "release_date": "2020-10-08",
      "original_language": "en",
      "original_title": "Run",
      "genre_ids": [
        27,
        9648,
        53
      ],
      "backdrop_path": "/4rsxZjZppytn0dWO1Yi002olVku.jpg",
      "adult": false,
      "overview": "A homeschooled teenager begins to suspect her mother is keeping a dark secret from her.",
      "poster_path": "/ducK4LxP6csEHqRv2VeXgvfS10m.jpg",
      "popularity": 20.574
    },
    {
      "id": 627076,
      "video": false,
      "vote_count": 7,
      "vote_average": 6.9,
      "title": "Savage",
      "release_date": "2020-08-27",
      "original_language": "en",
      "original_title": "Savage",
      "genre_ids": [
        18,
        80
      ],
      "backdrop_path": "/53eze0tucDBUdanar78rVCfIGvl.jpg",
      "adult": false,
      "overview": "Inspired by the true stories of New Zealand's street gangs across 30 years, we follow Danny at three defining moments in his life as he grows from a boy into the violent enforcer of a gang.",
      "poster_path": "/1XixAUZcBhdqxqkDAimbZQcLnHE.jpg",
      "popularity": 7.67
    },
    {
      "id": 646106,
      "video": false,
      "vote_count": 4,
      "vote_average": 7.8,
      "title": "The Crossing",
      "release_date": "2020-02-14",
      "original_language": "no",
      "original_title": "Flukten over grensen",
      "genre_ids": [
        53,
        10751,
        10752
      ],
      "backdrop_path": "/ooATGkHxlPXE1CTSAdeaHscuon6.jpg",
      "adult": false,
      "overview": "One day, just before Christmas in 1942, Gerda's and Otto's parents are arrested for being part of the Norwegian resistance movement during the  Second World War, leaving the siblings on their own. Following the arrest, they discover two Jewish children, Sarah and Daniel, hidden in a secret cupboard in their basement at home. It is now up to Gerda and Otto to finish what their parents started: To help Sarah and Daniel flee from the Nazis cross the border to neutral Sweden and reunite them with their parents.",
      "poster_path": "/x2gi261egmQ1pVs5CRly2AIgWAs.jpg",
      "popularity": 1.903
    },
    {
      "id": 627064,
      "video": false,
      "vote_count": 4,
      "vote_average": 7.5,
      "title": "Real",
      "release_date": "2019-10-06",
      "original_language": "en",
      "original_title": "Real",
      "genre_ids": [
        18,
        10749
      ],
      "backdrop_path": "/hqByiNd1gabtv94oF5OdiiwrL09.jpg",
      "adult": false,
      "overview": "Aki Omoshaybi’s earnest debut explores the love between two people who work hard to keep their romance on track while struggling to manage personal hardship.",
      "poster_path": "/qeIgzcCW4wBsdy9Fv824XDmi56r.jpg",
      "popularity": 6.994
    },
    {
      "id": 754053,
      "video": false,
      "vote_count": 5,
      "vote_average": 5.4,
      "title": "My Dads Christmas Date",
      "release_date": "2020-11-06",
      "original_language": "en",
      "original_title": "My Dad's Christmas Date",
      "genre_ids": [
        35
      ],
      "backdrop_path": "/auT7g27gXXE23jxu6VCLsYFb14r.jpg",
      "adult": false,
      "overview": "It’s Christmas and the charming city of York, home to Jules, 16 and her Dad, David is decked out ready for the festive season. In many ways, David and Jules’ relationship is no different from that of most fathers and their sixteen-year-old daughters. He struggles to understand her, she refuses to communicate with him. He wants to be involved in her life, she wants her own space. In one important respect, however, David and Jules share a profound bond: the death of Jules’ mum, and David’s wife, in a car crash two years before. With both struggling to cope with everyday life in the shadow of their loss, Jules, inspired by happy memories of her mum, decides to take matters into her own hands.",
      "poster_path": "/mAfeEGYIwEG4YmVk77pv8fk0Dpr.jpg",
      "popularity": 19.948
    },
    {
      "adult": false,
      "backdrop_path": "/8gJu9LA8R0LijzaeaYALgTEyQ3S.jpg",
      "genre_ids": [
        16,
        28,
        12,
        10751,
        35
      ],
      "id": 523604,
      "original_language": "en",
      "original_title": "The Legend of Hallowaiian",
      "overview": "After releasing a mythical monster upon the islands, a group of three young friends must use local legends to restore peace to their home.",
      "poster_path": "/kbrdooJBiYRMQuYSNntaNmkXNy7.jpg",
      "release_date": "2018-10-18",
      "title": "The Legend of Hallowaiian",
      "video": false,
      "vote_average": 8.8,
      "vote_count": 5,
      "popularity": 4.281
    },
    {
      "id": 566675,
      "video": false,
      "vote_count": 6,
      "vote_average": 6,
      "title": "Remember Me",
      "release_date": "2019-08-02",
      "original_language": "en",
      "original_title": "Remember Me",
      "genre_ids": [
        35,
        18,
        10749
      ],
      "backdrop_path": "/25fP9OL55kNc0TuBBEZ6MFg9wxu.jpg",
      "adult": false,
      "overview": "After discovering his old flame now has Alzheimer's, a hopelessly in love widower fakes his way into her senior living community in an effort to reunite with her.",
      "poster_path": "/97b0xywgjen8zENjyTWGukBYlAz.jpg",
      "popularity": 10.69
    },
    {
      "id": 664184,
      "video": false,
      "vote_count": 5,
      "vote_average": 5.3,
      "title": "The Opening Act",
      "release_date": "2020-10-16",
      "original_language": "en",
      "original_title": "The Opening Act",
      "genre_ids": [
        35
      ],
      "backdrop_path": "/2GhyI4g28OoT3QtaQL3S2FDSWtZ.jpg",
      "adult": false,
      "overview": "The film follows Will Chu whose true life passion is to become a stand-up comedian. He is given the opportunity to emcee a comedy show, opening for his hero, Billy G. Chu has to decide if he wants to continue the life he has set up or to pursue his dream, the life of a comedian.",
      "poster_path": "/dLzrhDvKXT7KVIDTglnTOhz19cW.jpg",
      "popularity": 12.652
    },
    {
      "id": 602211,
      "video": false,
      "vote_count": 2,
      "vote_average": 3,
      "title": "Fatman",
      "release_date": "2020-11-13",
      "original_language": "en",
      "original_title": "Fatman",
      "genre_ids": [
        28,
        35
      ],
      "backdrop_path": "/8TKusadLJHQfsfwTBN9UBoHtY8l.jpg",
      "adult": false,
      "overview": "A rowdy, unorthodox Santa Claus is fighting to save his declining business. Meanwhile, Billy, a neglected and precocious 12 year old, hires a hit man to kill Santa after receiving a lump of coal in his stocking.",
      "poster_path": "/xZR3dm2wdKEFWJrqIVFnNyjhAQX.jpg",
      "popularity": 17.453
    },
    {
      "adult": false,
      "backdrop_path": "/23XgY6hoKYnZK3oTChN4Xlp9kdO.jpg",
      "genre_ids": [
        35
      ],
      "id": 679898,
      "original_language": "es",
      "original_title": "La maldición del guapo",
      "overview": "Humberto, a seductive con artist, lives a quiet life in Madrid after having done his time for a big job that cost him his relationship with his son Jorge, who reaches out to him for help when the jewellery where he works is robbed.",
      "poster_path": "/7ABPIUINn2zZWtyW8xPsCppT7OB.jpg",
      "release_date": "2020-07-10",
      "title": "La maldición del guapo",
      "video": false,
      "vote_average": 6,
      "vote_count": 5,
      "popularity": 59.035
    },
    {
      "id": 593059,
      "video": false,
      "vote_count": 5,
      "vote_average": 6.8,
      "title": "A Piece of My Heart",
      "release_date": "2019-12-25",
      "original_language": "sv",
      "original_title": "En del av mitt hjärta",
      "genre_ids": [
        35
      ],
      "backdrop_path": "/hGjDoSHlBzPZcTbSBFfXGJHhUpJ.jpg",
      "adult": false,
      "overview": "Isabella (35) is a driven business woman in Stockholm’s finance world. When she goes home to the small town she grew up in to celebrate her father’s sixtieth birthday, she feels like a winner, the only one in her old school gang that things have really gone well for. Isabella’s selfconfidence falters when she realizes that Simon, her great love from her teen years, is going to marry Isabella’s childhood friend Molly, and that she isn’t invited to the wedding. Isabella is accustomed to getting what she wants, and it disturbs her to see Simon so happy with someone else. Unfortunately, there are some feelings that never go away.",
      "poster_path": "/etLJZWW3zw9avVoRr5rZWG6h2ak.jpg",
      "popularity": 15.523
    },
    {
      "id": 679796,
      "video": false,
      "vote_count": 14,
      "vote_average": 5.9,
      "title": "Enemy Lines",
      "release_date": "2020-04-24",
      "original_language": "en",
      "original_title": "Enemy Lines",
      "genre_ids": [
        10752,
        28
      ],
      "backdrop_path": "/eTb7csUniZdYr4dXGJYuLJRRBvm.jpg",
      "adult": false,
      "overview": "In the frozen, war torn landscape of occupied Poland during World War II, a crack team of allied commandos are sent on a deadly mission behind enemy lines to extract a rocket scientist from the hands of the Nazis.",
      "poster_path": "/vG8qBkByy9naORB6zahcntIC2N.jpg",
      "popularity": 109.041
    },
    {
      "id": 615790,
      "video": false,
      "vote_count": 4,
      "vote_average": 4.3,
      "title": "The Cleansing",
      "release_date": "2019-08-05",
      "original_language": "en",
      "original_title": "The Cleansing",
      "genre_ids": [
        18,
        27,
        53
      ],
      "backdrop_path": "/cnzo5jTthrAfRMPid8TpsZDG7W3.jpg",
      "adult": false,
      "overview": "Set in a small isolated village in 14th century Wales, Alice is a sixteen year old girl who is accused of being a witch and causing the plague that has ravaged the village, taking the lives of many, including Alice's own father. When it is revealed that Alice has been hiding her mother's infection, she is forced to watch The Cleanser, an ominous masked figure, brutally dispatch her mother. The town preacher and de-facto leader Tom has eyes for Alice, and subjects her to five torturous trials after she spurns his advances. Escaping the night before her execution, with the help of her mother's friend Mary, she flees into the forest and discovers the secluded hut of a mysterious healer, with his own troubled past and demons to face. He nurses her back to health, and teaches her how to exact revenge upon those that persecuted her.",
      "poster_path": "/woJfpWhsEdQ2cLkpF0L45b7RBQO.jpg",
      "popularity": 3.502
    },
    {
      "adult": false,
      "backdrop_path": "/wViqLlwLIvUAwE54KZHcBKPZKv4.jpg",
      "genre_ids": [
        35
      ],
      "id": 733906,
      "original_language": "en",
      "original_title": "John Bronco",
      "overview": "The unbelievable story of the rise, fall and ultimate redemption of the legendary pitchman for the Ford Bronco.",
      "poster_path": "/7jpPXcV4yUQeuRmgjmIRVQnEayQ.jpg",
      "release_date": "2020-10-15",
      "title": "John Bronco",
      "video": false,
      "vote_average": 5.2,
      "vote_count": 5,
      "popularity": 6.563
    },
    {
      "adult": false,
      "backdrop_path": "/A6ep53BjCg6vAQaEzXjrwMIzOs8.jpg",
      "genre_ids": [
        18
      ],
      "id": 646363,
      "original_language": "pl",
      "original_title": "Lubię mówić z Tobą",
      "overview": "Morgue worker starts talking to the dead.",
      "poster_path": "/vvQVxMpbMmuDbWKuPp8W7jMhBBL.jpg",
      "release_date": "2013-01-01",
      "title": "Lubię mówić z Tobą",
      "video": false,
      "vote_average": 5.5,
      "vote_count": 4,
      "popularity": 1.649
    },
    {
      "id": 505746,
      "video": false,
      "vote_count": 4,
      "vote_average": 6.8,
      "title": "Burnout 3",
      "release_date": "2020-10-14",
      "original_language": "no",
      "original_title": "Børning 3",
      "genre_ids": [
        28,
        35
      ],
      "backdrop_path": "/yvZUTqBQP7y0pzoPqBVF3fNczkh.jpg",
      "adult": false,
      "overview": "The third and final instalment in the Burnout trilogy. This time, the road leads trough Norway, to Sweden, Denmark and finally Germany to race on the famous racing track, Nürburgring.",
      "poster_path": "/A2NIXTSmGjqAqJmnFr5ZwpOva9l.jpg",
      "popularity": 6.987
    }
  ],
  "total_pages": 2,
  "total_results": 40
}"""

        const val similarMoviesResponse = """{
  "page": 1,
  "results": [
    {
      "id": 744,
      "video": false,
      "vote_count": 4348,
      "vote_average": 6.9,
      "title": "Top Gun",
      "release_date": "1986-05-16",
      "original_language": "en",
      "original_title": "Top Gun",
      "genre_ids": [
        28,
        18
      ],
      "backdrop_path": "/jILeJ60zPpIjjJHGSmIeY4eO30t.jpg",
      "adult": false,
      "overview": "For Lieutenant Pete 'Maverick' Mitchell and his friend and co-pilot Nick 'Goose' Bradshaw, being accepted into an elite training school for fighter pilots is a dream come true. But a tragedy, as well as personal demons, will threaten Pete's dreams of becoming an ace pilot.",
      "poster_path": "/xUuHj3CgmZQ9P2cMaqQs4J0d4Zc.jpg",
      "popularity": 28.709
    },
    {
      "adult": false,
      "backdrop_path": "/p01c4KaaI8oqmKQqfrQiqYlHR6W.jpg",
      "genre_ids": [
        28,
        53,
        9648
      ],
      "id": 146198,
      "original_language": "en",
      "original_title": "Triple 9",
      "overview": "A gang of criminals and corrupt cops plan the murder of a police officer in order to pull off their biggest heist yet across town.",
      "poster_path": "/kQY3cX8DhNtWDXoYfXnqp3imZ1U.jpg",
      "release_date": "2016-02-19",
      "title": "Triple 9",
      "video": false,
      "vote_average": 5.9,
      "vote_count": 1535,
      "popularity": 18.513
    },
    {
      "id": 787,
      "video": false,
      "vote_count": 7290,
      "vote_average": 6.7,
      "title": "Mr. & Mrs. Smith",
      "release_date": "2005-06-07",
      "original_language": "en",
      "original_title": "Mr. & Mrs. Smith",
      "genre_ids": [
        28,
        35,
        18,
        53
      ],
      "backdrop_path": "/gaZK3jT9sSsLfT479nGVXWxN0Hz.jpg",
      "adult": false,
      "overview": "After five (or six) years of vanilla-wedded bliss, ordinary suburbanites John and Jane Smith are stuck in a huge rut. Unbeknownst to each other, they are both coolly lethal, highly-paid assassins working for rival organisations. When they discover they're each other's next target, their secret lives collide in a spicy, explosive mix of wicked comedy, pent-up passion, nonstop action and high-tech weaponry.",
      "poster_path": "/eMftEiMCZPEtzzEFyYLjbNkgRQ5.jpg",
      "popularity": 24.82
    },
    {
      "id": 300671,
      "video": false,
      "vote_count": 2181,
      "vote_average": 7.2,
      "title": "13 Hours: The Secret Soldiers of Benghazi",
      "release_date": "2016-01-13",
      "original_language": "en",
      "original_title": "13 Hours: The Secret Soldiers of Benghazi",
      "genre_ids": [
        28,
        18,
        36,
        53,
        10752
      ],
      "backdrop_path": "/ayDMYGUNVvXS76wQgFwTiUIDNb5.jpg",
      "adult": false,
      "overview": "An American Ambassador is killed during an attack at a U.S. compound in Libya as a security team struggles to make sense out of the chaos.",
      "poster_path": "/4qnEeVPM8Yn5dIVC4k4yyjrUXeR.jpg",
      "popularity": 33.766
    },
    {
      "id": 10908,
      "video": false,
      "vote_count": 125,
      "vote_average": 5.6,
      "title": "Inferno",
      "release_date": "1999-09-25",
      "original_language": "en",
      "original_title": "Inferno",
      "genre_ids": [
        28,
        18,
        10749
      ],
      "backdrop_path": "/45iOWQhW9vdaoeETepClEaEVozk.jpg",
      "adult": false,
      "overview": "Eddie Lomax (Jean-Claude Van Damme) is a drifter who has been in a suicidal funk since the death of his close friend Johnny (Danny Trejo). Riding his motorcycle into a small desert town where Johnny once lived, Lomax is confronted by a gang of toughs, who beat him and steal his bike. However, Lomax is not a man to take an injustice lying down, and soon he begins exacting a violent revenge on the men who stole his motorcycle, with local handyman Jubal Early (Pat Morita) lending a hand and several area ladies offering aid and comfort.",
      "poster_path": "/dFlI0Vb4JOsRXG1JSS2Ufs6Sp8k.jpg",
      "popularity": 15.345
    },
    {
      "id": 121,
      "video": false,
      "vote_count": 15556,
      "vote_average": 8.3,
      "title": "The Lord of the Rings: The Two Towers",
      "release_date": "2002-12-18",
      "original_language": "en",
      "original_title": "The Lord of the Rings: The Two Towers",
      "genre_ids": [
        28,
        12,
        14
      ],
      "backdrop_path": "/9BUvLUz1GhbNpcyQRyZm1HNqMq4.jpg",
      "adult": false,
      "overview": "Frodo and Sam are trekking to Mordor to destroy the One Ring of Power while Gimli, Legolas and Aragorn search for the orc-captured Merry and Pippin. All along, nefarious wizard Saruman awaits the Fellowship members at the Orthanc Tower in Isengard.",
      "poster_path": "/5VTN0pR8gcqV3EPUHHfMGnJYN9L.jpg",
      "popularity": 53.331
    },
    {
      "id": 10015,
      "video": false,
      "vote_count": 568,
      "vote_average": 6.7,
      "title": "Heartbreak Ridge",
      "release_date": "1986-12-05",
      "original_language": "en",
      "original_title": "Heartbreak Ridge",
      "genre_ids": [
        28,
        35,
        18,
        10752
      ],
      "backdrop_path": "/91u48Ye19SesVaBuoaETtwa8CxK.jpg",
      "adult": false,
      "overview": "A hard-nosed, hard-living Marine gunnery sergeant clashes with his superiors and his ex-wife as he takes command of a spoiled recon platoon with a bad attitude.",
      "poster_path": "/x5qob9cZEPDcmd1Sc0PbvOp2OIg.jpg",
      "popularity": 14.611
    },
    {
      "id": 10839,
      "video": false,
      "vote_count": 206,
      "vote_average": 6.9,
      "title": "Cross of Iron",
      "release_date": "1977-01-29",
      "original_language": "en",
      "original_title": "Cross of Iron",
      "genre_ids": [
        18,
        28,
        36,
        10752
      ],
      "backdrop_path": "/edUMdGAWTwc6RA2uhvagZJkrhAw.jpg",
      "adult": false,
      "overview": "It is 1943, and the German army—ravaged and demoralised—is hastily retreating from the Russian front. In the midst of the madness, conflict brews between the aristocratic yet ultimately pusillanimous Captain Stransky and the courageous Corporal Steiner. Stransky is the only man who believes that the Third Reich is still vastly superior to the Russian army. However, within his pompous persona lies a quivering coward who longs for the Iron Cross so that he can return to Berlin a hero. Steiner, on the other hand is cynical, defiantly non-conformist and more concerned with the safety of his own men rather than the horde of military decorations offered to him by his superiors.",
      "poster_path": "/tpExYgspVudTQvC82m7kvePcXqr.jpg",
      "popularity": 11.325
    },
    {
      "id": 238636,
      "video": false,
      "vote_count": 4766,
      "vote_average": 6.6,
      "title": "The Purge: Anarchy",
      "release_date": "2014-07-17",
      "original_language": "en",
      "original_title": "The Purge: Anarchy",
      "genre_ids": [
        27,
        53
      ],
      "backdrop_path": "/kFFyh3mPukknIQBPMinoSeZIUOq.jpg",
      "adult": false,
      "overview": "One night per year, the government sanctions a 12-hour period in which citizens can commit any crime they wish -- including murder -- without fear of punishment or imprisonment. Leo, a sergeant who lost his son, plans a vigilante mission of revenge during the mayhem. However, instead of a death-dealing avenger, he becomes the unexpected protector of four innocent strangers who desperately need his help if they are to survive the night.",
      "poster_path": "/f2HD5iVhJWWv72QVWThUKk09zYy.jpg",
      "popularity": 71.908
    },
    {
      "id": 826,
      "video": false,
      "vote_count": 1238,
      "vote_average": 7.8,
      "title": "The Bridge on the River Kwai",
      "release_date": "1957-10-02",
      "original_language": "en",
      "original_title": "The Bridge on the River Kwai",
      "genre_ids": [
        18,
        36,
        10752
      ],
      "backdrop_path": "/EcQKZcVYZ8EjK3eN1quPvkMrL9.jpg",
      "adult": false,
      "overview": "The classic story of English POWs in Burma forced to build a bridge to aid the war effort of their Japanese captors. British and American intelligence officers conspire to blow up the structure, but Col. Nicholson , the commander who supervised the bridge's construction, has acquired a sense of pride in his creation and tries to foil their plans.",
      "poster_path": "/rVWacfczT3i1GOjqp2u4K9wahta.jpg",
      "popularity": 16.199
    },
    {
      "adult": false,
      "backdrop_path": "/fy8E5sF1Phf5aihA3nxx4Qqxotl.jpg",
      "genre_ids": [
        37,
        36,
        10752
      ],
      "id": 10733,
      "original_language": "en",
      "original_title": "The Alamo",
      "overview": "Based on the 1836 standoff between a group of Texan and Tejano men, led by Davy Crockett and Jim Bowie, and Mexican dictator Santa Anna's forces at the Alamo in San Antonio, Texas.",
      "poster_path": "/yU800quJAC1lQNcZGeTGD5QQtV.jpg",
      "release_date": "2004-04-07",
      "title": "The Alamo",
      "video": false,
      "vote_average": 5.8,
      "vote_count": 211,
      "popularity": 9.756
    },
    {
      "adult": false,
      "backdrop_path": "/3CMT4APmiXBuFn3NYUZVGoA4knF.jpg",
      "genre_ids": [
        18
      ],
      "id": 14626,
      "original_language": "fr",
      "original_title": "Beau travail",
      "overview": "Foreign Legion officer, Galoup, recalls his once glorious life, leading troops in the Gulf of Djibouti. His existence there was happy, strict and regimented, but the arrival of a promising young recruit, Sentain, plants the seeds of jealousy in Galoup's mind.",
      "poster_path": "/8liInyfcnyn0vizoQNGmJohtsCm.jpg",
      "release_date": "1999-09-16",
      "title": "Beau Travail",
      "video": false,
      "vote_average": 7,
      "vote_count": 104,
      "popularity": 8.398
    },
    {
      "id": 11589,
      "video": false,
      "vote_count": 400,
      "vote_average": 7.4,
      "title": "Kelly's Heroes",
      "release_date": "1970-06-22",
      "original_language": "en",
      "original_title": "Kelly's Heroes",
      "genre_ids": [
        12,
        35,
        10752
      ],
      "backdrop_path": "/js7WdYkmPDx8zHX0pgzhNuOTk77.jpg",
      "adult": false,
      "overview": "A misfit group of World War II American soldiers goes AWOL to rob a bank behind German lines.",
      "poster_path": "/hleMxFKSC42yT0TClS4G29HV11n.jpg",
      "popularity": 14.42
    },
    {
      "id": 10652,
      "video": false,
      "vote_count": 244,
      "vote_average": 6.6,
      "title": "Hamburger Hill",
      "release_date": "1987-08-07",
      "original_language": "en",
      "original_title": "Hamburger Hill",
      "genre_ids": [
        28,
        18,
        10752
      ],
      "backdrop_path": "/qkwlPlub5kx7NNz1rX8S3TKZUWe.jpg",
      "adult": false,
      "overview": "The men of Bravo Company are facing a battle that's all uphill… up Hamburger Hill. Fourteen war-weary soldiers are battling for a mud-covered mound of earth so named because it chews up soldiers like chopped meat. They are fighting for their country, their fellow soldiers and their lives. War is hell, but this is worse. Hamburger Hill tells it the way it was, the way it really was. It's a raw, gritty and totally unrelenting dramatic depiction of one of the fiercest battles of America's bloodiest war. This happened. Hamburger Hill - war at its worst, men at their best.",
      "poster_path": "/a84FDoIm64qcyKq6BEMB9Ycldpt.jpg",
      "popularity": 9.858
    },
    {
      "adult": false,
      "backdrop_path": "/qsN29Wb7sqw06I9TzEnJE3soxHM.jpg",
      "genre_ids": [
        18,
        10752
      ],
      "id": 438259,
      "original_language": "en",
      "original_title": "Journey's End",
      "overview": "Set in a dugout in Aisne in 1918, a group of British officers, led by the mentally disintegrating young officer Stanhope, variously await their fate.",
      "poster_path": "/5gGkt84bCPt7XRo35FcsrOeer78.jpg",
      "release_date": "2017-12-14",
      "title": "Journey's End",
      "video": false,
      "vote_average": 6.2,
      "vote_count": 149,
      "popularity": 11.7
    },
    {
      "id": 1722,
      "video": false,
      "vote_count": 330,
      "vote_average": 5.8,
      "title": "Captain Corelli's Mandolin",
      "release_date": "2001-04-18",
      "original_language": "en",
      "original_title": "Captain Corelli's Mandolin",
      "genre_ids": [
        18,
        36,
        10749
      ],
      "backdrop_path": "/6pOV0Ig0RF5AOUpIxv0YkAjBPh5.jpg",
      "adult": false,
      "overview": "When a Greek fisherman leaves to fight with the Greek army during WWII, his fiancee falls in love with the local Italian commander. The film is based on a novel about an Italian soldier's experiences during the Italian occupation of the Greek island of Cephalonia (Kefalonia), but Hollywood made it into a pure love story by removing much of the \"unpleasant\" stuff.",
      "poster_path": "/dNNjMmZwQvq48Pw71BIzd8ky6Rh.jpg",
      "popularity": 11.398
    },
    {
      "adult": false,
      "backdrop_path": "/5kmoreEiqaU0JoE8GZmLwLvAsJq.jpg",
      "genre_ids": [
        10749,
        18,
        10752
      ],
      "id": 433356,
      "original_language": "en",
      "original_title": "The Ottoman Lieutenant",
      "overview": "Lillie, a determined American woman, ventures overseas to join Dr. Jude at a remote medical mission in the Ottoman Empire (now Turkey). However, Lillie soon finds herself at odds with Jude and the mission’s founder, Woodruff, when she falls for the titular military man, Ismail, just as the war is about to erupt.",
      "poster_path": "/c5E8KTDuGNdF4Lmv3OhsQ0W1zPw.jpg",
      "release_date": "2017-03-10",
      "title": "The Ottoman Lieutenant",
      "video": false,
      "vote_average": 6.1,
      "vote_count": 174,
      "popularity": 12.795
    },
    {
      "id": 1829,
      "video": false,
      "vote_count": 93,
      "vote_average": 7,
      "title": "The Story of Adele H.",
      "release_date": "1975-10-08",
      "original_language": "fr",
      "original_title": "L'Histoire d'Adèle H.",
      "genre_ids": [
        18,
        36
      ],
      "backdrop_path": "/l74RI23VnGOjqBX7EjHk6gJ5NWp.jpg",
      "adult": false,
      "overview": "Adèle Hugo, daughter of renowned French writer Victor Hugo, falls in love with British soldier Albert Pinson while living in exile off the coast of England. Though he spurns her affections, she follows him to Nova Scotia and takes on the alias of Adèle Lewly. Albert continues to reject her, but she remains obsessive in her quest to win him over.",
      "poster_path": "/2t3mHLBNigYfwjIpiAGkWGdF7M3.jpg",
      "popularity": 8.912
    },
    {
      "id": 11702,
      "video": false,
      "vote_count": 253,
      "vote_average": 6,
      "title": "The Replacement Killers",
      "release_date": "1998-02-06",
      "original_language": "en",
      "original_title": "The Replacement Killers",
      "genre_ids": [
        28,
        80,
        18,
        9648,
        53
      ],
      "backdrop_path": "/yYY0eWwfbvb1OTUx5cMNvHogDvs.jpg",
      "adult": false,
      "overview": "Hired assassin John Lee is asked by Chinatown crime boss Terence Wei to murder the young son of policeman Stan Zedkov. Lee has the boy in his sights, but his conscience gets the better of him, and he spares the child's life. Afraid that Wei will take revenge on his family in China, Lee seeks out expert forger Meg Coburn to obtain the passport he needs to get out of the country, but a band of replacement killers is soon on his trail.",
      "poster_path": "/5JAVd0lYhkB2dsDtt84Qt6grNIn.jpg",
      "popularity": 10.752
    },
    {
      "id": 2623,
      "video": false,
      "vote_count": 665,
      "vote_average": 7,
      "title": "An Officer and a Gentleman",
      "release_date": "1982-07-28",
      "original_language": "en",
      "original_title": "An Officer and a Gentleman",
      "genre_ids": [
        18,
        10749
      ],
      "backdrop_path": "/xzyHYTcN4RPqk7gPzuguPFIjsG1.jpg",
      "adult": false,
      "overview": "Zack Mayo is an aloof, taciturn man who aspires to be a navy pilot. Once he arrives at training camp for his 13-week officer's course, Mayo runs afoul of abrasive, no-nonsense drill Sergeant Emil Foley. Mayo is an excellent cadet, but a little cold around the heart, so Foley rides him mercilessly, sensing that the young man would be prime officer material if he weren't so self-involved. Zack's affair with a working girl is likewise compromised by his unwillingness to give of himself.",
      "poster_path": "/nloIjLT5EFl4PUfbV6acxx4yiH7.jpg",
      "popularity": 9.605
    }
  ],
  "total_pages": 5,
  "total_results": 100
}"""

        const val favouriteMovies = """{
  "page": 1,
  "results": [
    {
      "id": 617505,
      "video": false,
      "vote_count": 753,
      "vote_average": 6.2,
      "title": "Hubie Halloween",
      "release_date": "2020-10-07",
      "original_language": "en",
      "original_title": "Hubie Halloween",
      "genre_ids": [
        35,
        14,
        9648,
        27
      ],
      "backdrop_path": "/aOeshAxAhiDVIiHsXVFmF6bgclh.jpg",
      "adult": false,
      "overview": "Hubie Dubois who, despite his devotion to his hometown of Salem, Massachusetts (and its legendary Halloween celebration), is a figure of mockery for kids and adults alike. But this year, something really is going bump in the night, and it’s up to Hubie to save Halloween.",
      "poster_path": "/dbhC6qRydXyRmpUdcl9bL9rARya.jpg",
      "popularity": 102.25
    },
    {
      "id": 637395,
      "video": false,
      "vote_count": 8,
      "vote_average": 5.9,
      "title": "The Spooky Tale of Captain Underpants Hack-a-ween",
      "release_date": "2019-10-08",
      "original_language": "en",
      "original_title": "The Spooky Tale of Captain Underpants Hack-a-ween",
      "genre_ids": [
        16,
        35,
        10751
      ],
      "backdrop_path": "/zb85xjZQTXa1AKA6AenN5gfIH2b.jpg",
      "adult": false,
      "overview": "When Melvin tries to cancel Halloween, clever best friends Harold and George create their own spooky holiday -- and it's a huge success!",
      "poster_path": "/qGuBwXcJhmFBQRu5F6EB2VoTCn2.jpg",
      "popularity": 26.929
    },
    {
      "id": 621151,
      "video": false,
      "vote_count": 32,
      "vote_average": 5.9,
      "title": "Spell",
      "release_date": "2020-10-30",
      "original_language": "en",
      "original_title": "Spell",
      "genre_ids": [
        53,
        27
      ],
      "backdrop_path": "/5gllGAa3c9UqeRI8r6GXiQJIEtp.jpg",
      "adult": false,
      "overview": "A father survives a plane crash in rural Appalachia, but becomes suspicious of the elderly couple who take him in to nurse him back to health with the ancient remedies.",
      "poster_path": "/4rjHhj1BAREc9zNFU8FheLJQdFf.jpg",
      "popularity": 353.318
    },
    {
      "id": 677638,
      "video": false,
      "vote_count": 514,
      "vote_average": 7.7,
      "title": "We Bare Bears: The Movie",
      "release_date": "2020-06-30",
      "original_language": "en",
      "original_title": "We Bare Bears: The Movie",
      "genre_ids": [
        10751,
        16,
        12,
        35
      ],
      "backdrop_path": "/pO1SnM5a1fEsYrFaVZW78Wb0zRJ.jpg",
      "adult": false,
      "overview": "When Grizz, Panda, and Ice Bear's love of food trucks and viral videos get out of hand, the brothers are chased away from their home and embark on a trip to Canada, where they can live in peace.",
      "poster_path": "/kPzcvxBwt7kEISB9O4jJEuBn72t.jpg",
      "popularity": 440.576
    },
    {
      "id": 539885,
      "video": false,
      "vote_count": 627,
      "vote_average": 5.7,
      "title": "Ava",
      "release_date": "2020-07-02",
      "original_language": "en",
      "original_title": "Ava",
      "genre_ids": [
        28,
        80,
        18,
        53
      ],
      "backdrop_path": "/54yOImQgj8i85u9hxxnaIQBRUuo.jpg",
      "adult": false,
      "overview": "A black ops assassin is forced to fight for her own survival after a job goes dangerously wrong.",
      "poster_path": "/qzA87Wf4jo1h8JMk9GilyIYvwsA.jpg",
      "popularity": 566.62
    },
    {
      "id": 694919,
      "video": false,
      "vote_count": 189,
      "vote_average": 5.9,
      "title": "Money Plane",
      "release_date": "2020-09-29",
      "original_language": "en",
      "original_title": "Money Plane",
      "genre_ids": [
        28
      ],
      "backdrop_path": "/pq0JSpwyT2URytdFG0euztQPAyR.jpg",
      "adult": false,
      "overview": "A professional thief with ${'$'}40 million in debt and his family's life on the line must commit one final heist - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
      "poster_path": "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
      "popularity": 542.264
    },
    {
      "id": 581392,
      "video": false,
      "vote_count": 774,
      "vote_average": 7,
      "title": "Peninsula",
      "release_date": "2020-07-15",
      "original_language": "ko",
      "original_title": "반도",
      "genre_ids": [
        28,
        27,
        53
      ],
      "backdrop_path": "/gEjNlhZhyHeto6Fy5wWy5Uk3A9D.jpg",
      "adult": false,
      "overview": "A soldier and his team battle hordes of post-apocalyptic zombies in the wastelands of the Korean Peninsula.",
      "poster_path": "/sy6DvAu72kjoseZEjocnm2ZZ09i.jpg",
      "popularity": 565.09
    },
    {
      "id": 740985,
      "video": false,
      "vote_count": 910,
      "vote_average": 6.6,
      "title": "Borat Subsequent Moviefilm",
      "release_date": "2020-10-23",
      "original_language": "en",
      "original_title": "Borat Subsequent Moviefilm",
      "genre_ids": [
        35
      ],
      "backdrop_path": "/hbrXbVoE0NuA1ORoSGGYNASagrl.jpg",
      "adult": false,
      "overview": "14 years after making a film about his journey across the USA, Borat risks life and limb when he returns to the United States with his young daughter, and reveals more about the culture, the COVID-19 pandemic, and the political elections.",
      "poster_path": "/6agKYU5IQFpuDyUYPu39w7UCRrJ.jpg",
      "popularity": 532.874
    },
    {
      "id": 337401,
      "video": false,
      "vote_count": 2915,
      "vote_average": 7.2,
      "title": "Mulan",
      "release_date": "2020-09-04",
      "original_language": "en",
      "original_title": "Mulan",
      "genre_ids": [
        28,
        12,
        18,
        14
      ],
      "backdrop_path": "/zzWGRw277MNoCs3zhyG3YmYQsXv.jpg",
      "adult": false,
      "overview": "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
      "poster_path": "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
      "popularity": 728.013
    },
    {
      "id": 624779,
      "video": false,
      "vote_count": 12,
      "vote_average": 4.9,
      "title": "VFW",
      "release_date": "2020-10-14",
      "original_language": "en",
      "original_title": "VFW",
      "genre_ids": [
        28,
        53,
        27
      ],
      "backdrop_path": "/h5sUE9jqoYrjsFjANJXL0gpZGye.jpg",
      "adult": false,
      "overview": "A typical night for veterans at a VFW turns into an all-out battle for survival when a desperate teen runs into the bar with a bag of stolen drugs. When a gang of violent punks come looking for her, the vets use every weapon at their disposal to protect the girl and themselves from an unrelenting attack.",
      "poster_path": "/AnVD7Gn14uOTQhcc5xYZGQ9DRvS.jpg",
      "popularity": 567.466
    },
    {
      "id": 560050,
      "video": false,
      "vote_count": 419,
      "vote_average": 7.6,
      "title": "Over the Moon",
      "release_date": "2020-10-16",
      "original_language": "en",
      "original_title": "Over the Moon",
      "genre_ids": [
        16,
        12,
        10751,
        14
      ],
      "backdrop_path": "/htBUhLSS7FfHtydgYxUWjL3J1Q1.jpg",
      "adult": false,
      "overview": "A girl builds a rocket to travel to the moon in hopes of meeting the legendary Moon Goddess.",
      "poster_path": "/lQfdytwN7eh0tXWjIiMceFdBBvD.jpg",
      "popularity": 601.785
    },
    {
      "id": 497582,
      "video": false,
      "vote_count": 2618,
      "vote_average": 7.5,
      "title": "Enola Holmes",
      "release_date": "2020-09-23",
      "original_language": "en",
      "original_title": "Enola Holmes",
      "genre_ids": [
        80,
        18,
        9648
      ],
      "backdrop_path": "/kMe4TKMDNXTKptQPAdOF0oZHq3V.jpg",
      "adult": false,
      "overview": "While searching for her missing mother, intrepid teen Enola Holmes uses her sleuthing skills to outsmart big brother Sherlock and help a runaway lord.",
      "poster_path": "/riYInlsq2kf1AWoGm80JQW5dLKp.jpg",
      "popularity": 788.325
    },
    {
      "id": 613504,
      "video": false,
      "vote_count": 1874,
      "vote_average": 7.3,
      "title": "After We Collided",
      "release_date": "2020-09-02",
      "original_language": "en",
      "original_title": "After We Collided",
      "genre_ids": [
        10749,
        18
      ],
      "backdrop_path": "/6hgItrYQEG33y0I7yP2SRl2ei4w.jpg",
      "adult": false,
      "overview": "Tessa finds herself struggling with her complicated relationship with Hardin; she faces a dilemma that could change their lives forever.",
      "poster_path": "/kiX7UYfOpYrMFSAGbI6j1pFkLzQ.jpg",
      "popularity": 810.348
    },
    {
      "id": 741067,
      "video": false,
      "vote_count": 157,
      "vote_average": 6.3,
      "title": "Welcome to Sudden Death",
      "release_date": "2020-09-29",
      "original_language": "en",
      "original_title": "Welcome to Sudden Death",
      "genre_ids": [
        28,
        53,
        12,
        18
      ],
      "backdrop_path": "/mc48QVtMhohMFrHGca8OHTB6C2B.jpg",
      "adult": false,
      "overview": "Jesse Freeman is a former special forces officer and explosives expert now working a regular job as a security guard in a state-of-the-art basketball arena. Trouble erupts when a tech-savvy cadre of terrorists kidnap the team's owner and Jesse's daughter during opening night. Facing a ticking clock and impossible odds, it's up to Jesse to not only save them but also a full house of fans in this highly charged action thriller.",
      "poster_path": "/elZ6JCzSEvFOq4gNjNeZsnRFsvj.jpg",
      "popularity": 851.543
    },
    {
      "id": 340102,
      "video": false,
      "vote_count": 720,
      "vote_average": 6.3,
      "title": "The New Mutants",
      "release_date": "2020-08-26",
      "original_language": "en",
      "original_title": "The New Mutants",
      "genre_ids": [
        28,
        878,
        27,
        12
      ],
      "backdrop_path": "/eCIvqa3QVCx6H09bdeOS8Al2Sqy.jpg",
      "adult": false,
      "overview": "Five young mutants, just discovering their abilities while held in a secret facility against their will, fight to escape their past sins and save themselves.",
      "poster_path": "/xZNw9xxtwbEf25NYoz52KdbXHPM.jpg",
      "popularity": 779.884
    },
    {
      "id": 741074,
      "video": false,
      "vote_count": 151,
      "vote_average": 7,
      "title": "Once Upon a Snowman",
      "release_date": "2020-10-23",
      "original_language": "en",
      "original_title": "Once Upon a Snowman",
      "genre_ids": [
        16,
        10751,
        35,
        14
      ],
      "backdrop_path": "/DA7gzvlBoxMNL0XmGgTZOyv67P.jpg",
      "adult": false,
      "overview": "The previously untold origins of Olaf, the innocent and insightful, summer-loving snowman are revealed as we follow Olaf’s first steps as he comes to life and searches for his identity in the snowy mountains outside Arendelle.",
      "poster_path": "/hddzYJtfYYeMDOQVcH58n8m1W3A.jpg",
      "popularity": 752.452
    },
    {
      "id": 528085,
      "video": false,
      "vote_count": 338,
      "vote_average": 4.7,
      "title": "2067",
      "release_date": "2020-10-01",
      "original_language": "en",
      "original_title": "2067",
      "genre_ids": [
        878,
        53,
        18
      ],
      "backdrop_path": "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
      "adult": false,
      "overview": "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
      "poster_path": "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
      "popularity": 1377.176
    },
    {
      "id": 531219,
      "video": false,
      "vote_count": 709,
      "vote_average": 6.9,
      "title": "Roald Dahl's The Witches",
      "release_date": "2020-10-26",
      "original_language": "en",
      "original_title": "Roald Dahl's The Witches",
      "genre_ids": [
        14,
        10751,
        12,
        35,
        27
      ],
      "backdrop_path": "/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
      "adult": false,
      "overview": "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
      "poster_path": "/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
      "popularity": 1108.384
    }
  ],
  "total_pages": 1,
  "total_results": 18
}"""

        const val watchListMovies = """{
  "page": 1,
  "results": [
    {
      "id": 671145,
      "video": false,
      "vote_count": 21,
      "vote_average": 6.7,
      "title": "Before the Fire",
      "release_date": "2020-03-14",
      "original_language": "en",
      "original_title": "Before the Fire",
      "genre_ids": [
        28,
        878,
        53
      ],
      "backdrop_path": "/rq9kv233eEKnYfe8r5fi8SP5T7e.jpg",
      "adult": false,
      "overview": "Deep in the throes of a global pandemic, up and coming TV star, Ava Boone, is forced to flee the mounting chaos in Los Angeles and return to her rural hometown.  But as she struggles to acclimate to a way of life she left behind long ago, her homecoming attracts a dangerous figure from her past – threatening both her and the family that serves as her only sanctuary.",
      "poster_path": "/bAwtbmCzhFwKf2wN6QQH301ylbO.jpg",
      "popularity": 178.276
    },
    {
      "adult": false,
      "backdrop_path": "/AbtsLdz1gUj2H1HJJ3TRaBOl8Ta.jpg",
      "genre_ids": [
        28
      ],
      "id": 724717,
      "original_language": "en",
      "original_title": "The 2nd",
      "overview": "Secret-service agent Vic Davis is on his way to pick up his estranged son, Sean, from his college campus when he finds himself in the middle of a high-stakes terrorist operation. His son's friend Erin Walton, the daughter of Supreme Court Justice Walton is the target, and this armed faction will stop at nothing to kidnap her and use her as leverage for a pending landmark legal case.",
      "poster_path": "/o1WvNhoackad1QiAGRgjJCQ1Trj.jpg",
      "release_date": "2020-09-01",
      "title": "The 2nd",
      "video": false,
      "vote_average": 4.5,
      "vote_count": 21,
      "popularity": 17.471
    },
    {
      "id": 539885,
      "video": false,
      "vote_count": 627,
      "vote_average": 5.7,
      "title": "Ava",
      "release_date": "2020-07-02",
      "original_language": "en",
      "original_title": "Ava",
      "genre_ids": [
        28,
        80,
        18,
        53
      ],
      "backdrop_path": "/54yOImQgj8i85u9hxxnaIQBRUuo.jpg",
      "adult": false,
      "overview": "A black ops assassin is forced to fight for her own survival after a job goes dangerously wrong.",
      "poster_path": "/qzA87Wf4jo1h8JMk9GilyIYvwsA.jpg",
      "popularity": 566.62
    },
    {
      "id": 694919,
      "video": false,
      "vote_count": 189,
      "vote_average": 5.9,
      "title": "Money Plane",
      "release_date": "2020-09-29",
      "original_language": "en",
      "original_title": "Money Plane",
      "genre_ids": [
        28
      ],
      "backdrop_path": "/pq0JSpwyT2URytdFG0euztQPAyR.jpg",
      "adult": false,
      "overview": "A professional thief with ${'$'}40 million in debt and his family's life on the line must commit one final heist - rob a futuristic airborne casino filled with the world's most dangerous criminals.",
      "poster_path": "/6CoRTJTmijhBLJTUNoVSUNxZMEI.jpg",
      "popularity": 542.264
    },
    {
      "id": 624779,
      "video": false,
      "vote_count": 12,
      "vote_average": 4.9,
      "title": "VFW",
      "release_date": "2020-10-14",
      "original_language": "en",
      "original_title": "VFW",
      "genre_ids": [
        28,
        53,
        27
      ],
      "backdrop_path": "/h5sUE9jqoYrjsFjANJXL0gpZGye.jpg",
      "adult": false,
      "overview": "A typical night for veterans at a VFW turns into an all-out battle for survival when a desperate teen runs into the bar with a bag of stolen drugs. When a gang of violent punks come looking for her, the vets use every weapon at their disposal to protect the girl and themselves from an unrelenting attack.",
      "poster_path": "/AnVD7Gn14uOTQhcc5xYZGQ9DRvS.jpg",
      "popularity": 567.466
    },
    {
      "id": 340102,
      "video": false,
      "vote_count": 720,
      "vote_average": 6.3,
      "title": "The New Mutants",
      "release_date": "2020-08-26",
      "original_language": "en",
      "original_title": "The New Mutants",
      "genre_ids": [
        28,
        878,
        27,
        12
      ],
      "backdrop_path": "/eCIvqa3QVCx6H09bdeOS8Al2Sqy.jpg",
      "adult": false,
      "overview": "Five young mutants, just discovering their abilities while held in a secret facility against their will, fight to escape their past sins and save themselves.",
      "poster_path": "/xZNw9xxtwbEf25NYoz52KdbXHPM.jpg",
      "popularity": 779.884
    },
    {
      "id": 497582,
      "video": false,
      "vote_count": 2618,
      "vote_average": 7.5,
      "title": "Enola Holmes",
      "release_date": "2020-09-23",
      "original_language": "en",
      "original_title": "Enola Holmes",
      "genre_ids": [
        80,
        18,
        9648
      ],
      "backdrop_path": "/kMe4TKMDNXTKptQPAdOF0oZHq3V.jpg",
      "adult": false,
      "overview": "While searching for her missing mother, intrepid teen Enola Holmes uses her sleuthing skills to outsmart big brother Sherlock and help a runaway lord.",
      "poster_path": "/riYInlsq2kf1AWoGm80JQW5dLKp.jpg",
      "popularity": 788.325
    },
    {
      "id": 571384,
      "video": false,
      "vote_count": 39,
      "vote_average": 6.6,
      "title": "Come Play",
      "release_date": "2020-10-28",
      "original_language": "en",
      "original_title": "Come Play",
      "genre_ids": [
        27
      ],
      "backdrop_path": "/5HahZPsGGaDgnFb68J49ZwdwU0b.jpg",
      "adult": false,
      "overview": "A lonely young boy feels different from everyone else. Desperate for a friend, he seeks solace and refuge in his ever-present cell phone and tablet. When a mysterious creature uses the boy’s devices against him to break into our world, his parents must fight to save their son from the monster beyond the screen.",
      "poster_path": "/e98dJUitAoKLwmzjQ0Yxp1VQrnU.jpg",
      "popularity": 639.243
    },
    {
      "id": 613504,
      "video": false,
      "vote_count": 1874,
      "vote_average": 7.3,
      "title": "After We Collided",
      "release_date": "2020-09-02",
      "original_language": "en",
      "original_title": "After We Collided",
      "genre_ids": [
        10749,
        18
      ],
      "backdrop_path": "/6hgItrYQEG33y0I7yP2SRl2ei4w.jpg",
      "adult": false,
      "overview": "Tessa finds herself struggling with her complicated relationship with Hardin; she faces a dilemma that could change their lives forever.",
      "poster_path": "/kiX7UYfOpYrMFSAGbI6j1pFkLzQ.jpg",
      "popularity": 810.348
    },
    {
      "id": 337401,
      "video": false,
      "vote_count": 2915,
      "vote_average": 7.2,
      "title": "Mulan",
      "release_date": "2020-09-04",
      "original_language": "en",
      "original_title": "Mulan",
      "genre_ids": [
        28,
        12,
        18,
        14
      ],
      "backdrop_path": "/zzWGRw277MNoCs3zhyG3YmYQsXv.jpg",
      "adult": false,
      "overview": "When the Emperor of China issues a decree that one man per family must serve in the Imperial Chinese Army to defend the country from Huns, Hua Mulan, the eldest daughter of an honored warrior, steps in to take the place of her ailing father. She is spirited, determined and quick on her feet. Disguised as a man by the name of Hua Jun, she is tested every step of the way and must harness her innermost strength and embrace her true potential.",
      "poster_path": "/aKx1ARwG55zZ0GpRvU2WrGrCG9o.jpg",
      "popularity": 728.013
    },
    {
      "id": 618353,
      "video": false,
      "vote_count": 64,
      "vote_average": 7.9,
      "title": "Batman: Death in the Family",
      "release_date": "2020-10-13",
      "original_language": "en",
      "original_title": "Batman: Death in the Family",
      "genre_ids": [
        16,
        28
      ],
      "backdrop_path": "/kU7ZiyeUwcpTkYj3UcxSqGdZmxY.jpg",
      "adult": false,
      "overview": "Tragedy strikes the Batman's life again when Robin Jason Todd tracks down his birth mother only to run afoul of the Joker. An adaptation of the 1988 comic book storyline of the same name.",
      "poster_path": "/k8Q9ulyRE8fkvZMkAM9LPYMKctb.jpg",
      "popularity": 600.043
    },
    {
      "id": 741074,
      "video": false,
      "vote_count": 151,
      "vote_average": 7,
      "title": "Once Upon a Snowman",
      "release_date": "2020-10-23",
      "original_language": "en",
      "original_title": "Once Upon a Snowman",
      "genre_ids": [
        16,
        10751,
        35,
        14
      ],
      "backdrop_path": "/DA7gzvlBoxMNL0XmGgTZOyv67P.jpg",
      "adult": false,
      "overview": "The previously untold origins of Olaf, the innocent and insightful, summer-loving snowman are revealed as we follow Olaf’s first steps as he comes to life and searches for his identity in the snowy mountains outside Arendelle.",
      "poster_path": "/hddzYJtfYYeMDOQVcH58n8m1W3A.jpg",
      "popularity": 752.452
    },
    {
      "id": 528085,
      "video": false,
      "vote_count": 338,
      "vote_average": 4.7,
      "title": "2067",
      "release_date": "2020-10-01",
      "original_language": "en",
      "original_title": "2067",
      "genre_ids": [
        878,
        53,
        18
      ],
      "backdrop_path": "/5UkzNSOK561c2QRy2Zr4AkADzLT.jpg",
      "adult": false,
      "overview": "A lowly utility worker is called to the future by a mysterious radio signal, he must leave his dying wife to embark on a journey that will force him to face his deepest fears in an attempt to change the fabric of reality and save humankind from its greatest environmental crisis yet.",
      "poster_path": "/7D430eqZj8y3oVkLFfsWXGRcpEG.jpg",
      "popularity": 1377.176
    },
    {
      "id": 531219,
      "video": false,
      "vote_count": 709,
      "vote_average": 6.9,
      "title": "Roald Dahl's The Witches",
      "release_date": "2020-10-26",
      "original_language": "en",
      "original_title": "Roald Dahl's The Witches",
      "genre_ids": [
        14,
        10751,
        12,
        35,
        27
      ],
      "backdrop_path": "/8rIoyM6zYXJNjzGseT3MRusMPWl.jpg",
      "adult": false,
      "overview": "In late 1967, a young orphaned boy goes to live with his loving grandma in the rural Alabama town of Demopolis. As the boy and his grandmother encounter some deceptively glamorous but thoroughly diabolical witches, she wisely whisks him away to a seaside resort. Regrettably, they arrive at precisely the same time that the world's Grand High Witch has gathered.",
      "poster_path": "/betExZlgK0l7CZ9CsCBVcwO1OjL.jpg",
      "popularity": 1108.384
    },
    {
      "id": 671039,
      "video": false,
      "vote_count": 200,
      "vote_average": 6.1,
      "title": "Rogue City",
      "release_date": "2020-10-30",
      "original_language": "fr",
      "original_title": "Bronx",
      "genre_ids": [
        53,
        28,
        18,
        80
      ],
      "backdrop_path": "/gnf4Cb2rms69QbCnGFJyqwBWsxv.jpg",
      "adult": false,
      "overview": "Caught in the crosshairs of police corruption and Marseille’s warring gangs, a loyal cop must protect his squad by taking matters into his own hands.",
      "poster_path": "/9HT9982bzgN5on1sLRmc1GMn6ZC.jpg",
      "popularity": 1387.376
    },
    {
      "id": 400160,
      "video": false,
      "vote_count": 1395,
      "vote_average": 8.1,
      "title": "The SpongeBob Movie: Sponge on the Run",
      "release_date": "2020-08-14",
      "original_language": "en",
      "original_title": "The SpongeBob Movie: Sponge on the Run",
      "genre_ids": [
        14,
        16,
        12,
        35,
        10751
      ],
      "backdrop_path": "/wu1uilmhM4TdluKi2ytfz8gidHf.jpg",
      "adult": false,
      "overview": "When his best friend Gary is suddenly snatched away, SpongeBob takes Patrick on a madcap mission far beyond Bikini Bottom to save their pink-shelled pal.",
      "poster_path": "/jlJ8nDhMhCYJuzOw3f52CP1W8MW.jpg",
      "popularity": 1377.738
    },
    {
      "id": 724989,
      "video": false,
      "vote_count": 151,
      "vote_average": 5,
      "title": "Hard Kill",
      "release_date": "2020-10-23",
      "original_language": "en",
      "original_title": "Hard Kill",
      "genre_ids": [
        28,
        53
      ],
      "backdrop_path": "/86L8wqGMDbwURPni2t7FQ0nDjsH.jpg",
      "adult": false,
      "overview": "The work of billionaire tech CEO Donovan Chalmers is so valuable that he hires mercenaries to protect it, and a terrorist group kidnaps his daughter just to get it.",
      "poster_path": "/ugZW8ocsrfgI95pnQ7wrmKDxIe.jpg",
      "popularity": 1345.086
    }
  ],
  "total_pages": 1,
  "total_results": 17
}"""

        const val detailTVShowResponse = """{
  "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
  "created_by": [
    {
      "id": 63554,
      "credit_id": "5d77e07539549a000f96cc12",
      "name": "Dave Erickson",
      "gender": 2,
      "profile_path": null
    },
    {
      "id": 1223867,
      "credit_id": "5521364b9251417be2002abc",
      "name": "Robert Kirkman",
      "gender": 2,
      "profile_path": "/ulYOkxtk8lUvg4Ltkg2q22ibg4R.jpg"
    }
  ],
  "episode_run_time": [
    43,
    60
  ],
  "first_air_date": "2015-08-23",
  "genres": [
    {
      "id": 10759,
      "name": "Action & Adventure"
    },
    {
      "id": 18,
      "name": "Drama"
    }
  ],
  "homepage": "http://www.amc.com/shows/fear-the-walking-dead",
  "id": 62286,
  "in_production": true,
  "languages": [
    "en",
    "es"
  ],
  "last_air_date": "2020-11-15",
  "last_episode_to_air": {
    "air_date": "2020-11-15",
    "episode_number": 6,
    "id": 2370332,
    "name": "Bury Her Next to Jasper's Leg",
    "overview": "A deadly explosion in the oil fields sends June on a mission to save as many lives as possible. Meanwhile, an investigation by Virginia threatens to undermine June's work.",
    "production_code": "",
    "season_number": 6,
    "still_path": "/aSWLDpD6nX4r2VX09AhLjH3mklV.jpg",
    "vote_average": 5,
    "vote_count": 2
  },
  "name": "Fear the Walking Dead",
  "next_episode_to_air": {
    "air_date": "2020-11-22",
    "episode_number": 7,
    "id": 2370333,
    "name": "Damage From the Inside",
    "overview": "When Dakota goes missing, Strand sends Alicia and Charlie on a search and rescue mission to find her. An unlikely ally provides a new possibility of escape from Virginia.",
    "production_code": "",
    "season_number": 6,
    "still_path": null,
    "vote_average": 0,
    "vote_count": 0
  },
  "networks": [
    {
      "name": "AMC",
      "id": 174,
      "logo_path": "/pmvRmATOCaDykE6JrVoeYxlFHw3.png",
      "origin_country": "US"
    }
  ],
  "number_of_episodes": 77,
  "number_of_seasons": 6,
  "origin_country": [
    "US"
  ],
  "original_language": "en",
  "original_name": "Fear the Walking Dead",
  "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
  "popularity": 627.312,
  "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg",
  "production_companies": [
    {
      "id": 11533,
      "logo_path": "/tWM9pmzVYxok4GbQIttxdcml1yT.png",
      "name": "Valhalla Motion Pictures",
      "origin_country": "US"
    },
    {
      "id": 23242,
      "logo_path": "/fOALFvgnO1ZdIaA9PNIAAuaDKWd.png",
      "name": "AMC Networks",
      "origin_country": "US"
    },
    {
      "id": 43346,
      "logo_path": null,
      "name": "Fox Productions",
      "origin_country": "US"
    },
    {
      "id": 23921,
      "logo_path": "/simDvqT8y6jhP530ggUMbikvVKc.png",
      "name": "Circle of Confusion",
      "origin_country": "US"
    }
  ],
  "seasons": [
    {
      "air_date": "2015-10-04",
      "episode_count": 38,
      "id": 70218,
      "name": "Specials",
      "overview": "Fear the Walking Dead: Flight 462 follows the story of a group of airline passengers who discover that one of their fellow travelers is infected with the virus, putting their lives at risk.\n\nA new installment of the 16-part series is available every sunday online, and on-air as promos during two commercial breaks of The Walking Dead.",
      "poster_path": "/nlFKhmcDK0JVmvqvxqmOeRZsJVU.jpg",
      "season_number": 0
    },
    {
      "air_date": "2015-08-23",
      "episode_count": 6,
      "id": 65692,
      "name": "Season 1",
      "overview": "After a string of ominous warnings, guidance counselor Madison Clark and the rest of her family are horrified to see their world descend into a zombie nightmare -- which will soon become their new reality.",
      "poster_path": "/i2bXSzpKWw0RVmLdldhBFT3a0Ty.jpg",
      "season_number": 1
    },
    {
      "air_date": "2016-04-10",
      "episode_count": 15,
      "id": 73780,
      "name": "Season 2",
      "overview": "Season two returns aboard the Abigail. Abandoning land, the group sets out for ports unknown, some place where Infection has not hit. They will discover that the water may be no safer than land.",
      "poster_path": "/oxAm6varqgV3WOGYX2OrgCGKnZo.jpg",
      "season_number": 2
    },
    {
      "air_date": "2017-06-04",
      "episode_count": 16,
      "id": 87822,
      "name": "Season 3",
      "overview": "As Fear the Walking Dead returns for season three, our families will be brought together in the vibrant and violent ecotone of the U.S.-Mexico border.",
      "poster_path": "/cMh46P517YVBedpMtO3ucBvK1jM.jpg",
      "season_number": 3
    },
    {
      "air_date": "2018-04-15",
      "episode_count": 16,
      "id": 99582,
      "name": "Season 4",
      "overview": "In season four, we see the world of Madison Clark and her family through new eyes -- the eyes of Morgan Jones. The characters' immediate past mixes with an uncertain present of struggle and discovery as they meet new friends, foes and threats. They fight for each other, against each other and against a legion of the dead to somehow build an existence against the crushing pressure of lives coming apart. There will be darkness and light; terror and grace; the heroic, mercenary, and craven, all crashing together towards a new reality.",
      "poster_path": "/k8T1iiKU9zQ8NAvwsB95KZXJ3O6.jpg",
      "season_number": 4
    },
    {
      "air_date": "2019-06-02",
      "episode_count": 16,
      "id": 121629,
      "name": "Season 5",
      "overview": "In season 5, the mission to help others will be put to the ultimate test when our group lands in uncharted territory. They will be forced to face not just their pasts but also their fears, leaving them forever changed.",
      "poster_path": "/aUBFD7zPl4fyE8e4FjASq4OgOUc.jpg",
      "season_number": 5
    },
    {
      "air_date": "2020-10-11",
      "episode_count": 8,
      "id": 157589,
      "name": "Season 6",
      "overview": "Heading into Season 5 of \"Fear the Walking Dead,\" the group's mission is clear: locate survivors and help make what's left of the world a slightly better place. With dogged determination, Morgan Jones leads the group with a philosophy rooted in benevolence, community and hope. Each character believes that helping others will allow them to make up for the wrongs of their pasts. But trust won't be easily earned. Their mission of helping others will be put to the ultimate test when the members of the group find themselves in uncharted territory, forced to face not just their pasts but also their fears. It is only by facing those fears that the group will discover an entirely new way to live, one that will leave them changed forever changed.",
      "poster_path": "/66zTxl3DMy1WH4U4SoeTkl5Yvpe.jpg",
      "season_number": 6
    }
  ],
  "status": "Returning Series",
  "tagline": "Every decision is life or death.",
  "type": "Scripted",
  "vote_average": 7.4,
  "vote_count": 2623
}"""

        const val popularTvResponse = """{
  "page": 1,
  "total_results": 10000,
  "total_pages": 500,
  "results": [
    {
      "original_name": "Fear the Walking Dead",
      "genre_ids": [
        10759,
        18
      ],
      "name": "Fear the Walking Dead",
      "popularity": 742.468,
      "origin_country": [
        "US"
      ],
      "vote_count": 2411,
      "first_air_date": "2015-08-23",
      "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
      "original_language": "en",
      "id": 62286,
      "vote_average": 7.4,
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg"
    },
    {
      "original_name": "The Boys",
      "genre_ids": [
        10765,
        10759
      ],
      "name": "The Boys",
      "popularity": 725.712,
      "origin_country": [
        "US"
      ],
      "vote_count": 3302,
      "first_air_date": "2019-07-25",
      "backdrop_path": "/mGVrXeIjyecj6TKmwPVpHlscEmw.jpg",
      "original_language": "en",
      "id": 76479,
      "vote_average": 8.4,
      "overview": "A group of vigilantes known informally as “The Boys” set out to take down corrupt superheroes with no more than blue-collar grit and a willingness to fight dirty.",
      "poster_path": "/mY7SeH4HFFxW1hiI6cWuwCRKptN.jpg"
    },
    {
      "original_name": "Cobra Kai",
      "genre_ids": [
        18,
        10759
      ],
      "name": "Cobra Kai",
      "popularity": 662.769,
      "origin_country": [
        "US"
      ],
      "vote_count": 1010,
      "first_air_date": "2018-05-02",
      "backdrop_path": "/g63KmFgqkvXu6WKS23V56hqEidh.jpg",
      "original_language": "en",
      "id": 77169,
      "vote_average": 8,
      "overview": "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
      "poster_path": "/eTMMU2rKpZRbo9ERytyhwatwAjz.jpg"
    },
    {
      "original_name": "Lucifer",
      "genre_ids": [
        80,
        10765
      ],
      "name": "Lucifer",
      "popularity": 638.314,
      "origin_country": [
        "US"
      ],
      "vote_count": 6033,
      "first_air_date": "2016-01-25",
      "backdrop_path": "/ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg",
      "original_language": "en",
      "id": 63174,
      "vote_average": 8.5,
      "overview": "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
      "poster_path": "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg"
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "name": "The Walking Dead: World Beyond",
      "popularity": 505.253,
      "origin_country": [
        "US"
      ],
      "vote_count": 305,
      "first_air_date": "2020-10-04",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "original_language": "en",
      "id": 94305,
      "vote_average": 7.8,
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg"
    },
    {
      "original_name": "Grey's Anatomy",
      "genre_ids": [
        18
      ],
      "name": "Grey's Anatomy",
      "popularity": 396.725,
      "origin_country": [
        "US"
      ],
      "vote_count": 3908,
      "first_air_date": "2005-03-27",
      "backdrop_path": "/edmk8xjGBsYVIf4QtLY9WMaMcXZ.jpg",
      "original_language": "en",
      "id": 1416,
      "vote_average": 8,
      "overview": "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
      "poster_path": "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg"
    },
    {
      "original_name": "The 100",
      "genre_ids": [
        18,
        10759,
        10765
      ],
      "name": "The 100",
      "popularity": 359.085,
      "origin_country": [
        "US"
      ],
      "vote_count": 4747,
      "first_air_date": "2014-03-19",
      "backdrop_path": "/hTExot1sfn7dHZjGrk0Aiwpntxt.jpg",
      "original_language": "en",
      "id": 48866,
      "vote_average": 7.8,
      "overview": "100 years in the future, when the Earth has been abandoned due to radioactivity, the last surviving humans live on an ark orbiting the planet — but the ark won't last forever. So the repressive regime picks 100 expendable juvenile delinquents to send down to Earth to see if the planet is still habitable.",
      "poster_path": "/wcaDIAG1QdXQLRaj4vC1EFdBT2.jpg"
    },
    {
      "original_name": "Riverdale",
      "genre_ids": [
        18,
        9648
      ],
      "name": "Riverdale",
      "popularity": 355.087,
      "origin_country": [
        "US"
      ],
      "vote_count": 6126,
      "first_air_date": "2017-01-26",
      "backdrop_path": "/9hvhGtcsGhQY58pukw8w55UEUbL.jpg",
      "original_language": "en",
      "id": 69050,
      "vote_average": 8.6,
      "overview": "Set in the present, the series offers a bold, subversive take on Archie, Betty, Veronica and their friends, exploring the surreality of small-town life, the darkness and weirdness bubbling beneath Riverdale’s wholesome facade.",
      "poster_path": "/4X7o1ssOEvp4BFLim1AZmPNcYbU.jpg"
    },
    {
      "original_name": "Game of Thrones",
      "genre_ids": [
        18,
        9648,
        10759,
        10765
      ],
      "name": "Game of Thrones",
      "popularity": 332.544,
      "origin_country": [
        "US"
      ],
      "vote_count": 11148,
      "first_air_date": "2011-04-17",
      "backdrop_path": "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
      "original_language": "en",
      "id": 1399,
      "vote_average": 8.3,
      "overview": "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
      "poster_path": "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg"
    },
    {
      "original_name": "The Simpsons",
      "genre_ids": [
        16,
        35,
        18,
        10751
      ],
      "name": "The Simpsons",
      "popularity": 332.193,
      "origin_country": [
        "US"
      ],
      "vote_count": 5026,
      "first_air_date": "1989-12-16",
      "backdrop_path": "/adZ9ldSlkGfLfsHNbh37ZThCcgU.jpg",
      "original_language": "en",
      "id": 456,
      "vote_average": 7.7,
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "poster_path": "/qcr9bBY6MVeLzriKCmJOv1562uY.jpg"
    },
    {
      "original_name": "Supernatural",
      "genre_ids": [
        18,
        9648,
        10765
      ],
      "name": "Supernatural",
      "popularity": 329.016,
      "origin_country": [
        "US"
      ],
      "vote_count": 4087,
      "first_air_date": "2005-09-13",
      "backdrop_path": "/nVRyd8hlg0ZLxBn9RaI7mUMQLnz.jpg",
      "original_language": "en",
      "id": 1622,
      "vote_average": 8.1,
      "overview": "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way. ",
      "poster_path": "/KoYWXbnYuS3b0GyQPkbuexlVK9.jpg"
    },
    {
      "original_name": "The Vampire Diaries",
      "genre_ids": [
        18,
        14,
        27,
        10749
      ],
      "name": "The Vampire Diaries",
      "popularity": 328.811,
      "origin_country": [
        "US"
      ],
      "vote_count": 3925,
      "first_air_date": "2009-09-10",
      "backdrop_path": "/k7T9xRyzP41wBVNyNeLmh8Ch7gD.jpg",
      "original_language": "en",
      "id": 18165,
      "vote_average": 8.3,
      "overview": "The story of two vampire brothers obsessed with the same girl, who bears a striking resemblance to the beautiful but ruthless vampire they knew and loved in 1864.",
      "poster_path": "/b3vl6wV1W8PBezFfntKTrhrehCY.jpg"
    },
    {
      "original_name": "The Walking Dead",
      "genre_ids": [
        18,
        10759,
        10765
      ],
      "name": "The Walking Dead",
      "popularity": 325.769,
      "origin_country": [
        "US"
      ],
      "vote_count": 8336,
      "first_air_date": "2010-10-31",
      "backdrop_path": "/wXXaPMgrv96NkH8KD1TMdS2d7iq.jpg",
      "original_language": "en",
      "id": 1402,
      "vote_average": 7.9,
      "overview": "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
      "poster_path": "/qgjP2OrrX9gc6M270xdPnEmE9tC.jpg"
    },
    {
      "original_name": "The Good Doctor",
      "genre_ids": [
        18
      ],
      "name": "The Good Doctor",
      "popularity": 302.957,
      "origin_country": [
        "US"
      ],
      "vote_count": 5263,
      "first_air_date": "2017-09-25",
      "backdrop_path": "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
      "original_language": "en",
      "id": 71712,
      "vote_average": 8.6,
      "overview": "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
      "poster_path": "/53P8oHo9cfOsgb1cLxBi4pFY0ja.jpg"
    },
    {
      "original_name": "Bones",
      "genre_ids": [
        80,
        18
      ],
      "name": "Bones",
      "popularity": 280.557,
      "origin_country": [
        "US"
      ],
      "vote_count": 1452,
      "first_air_date": "2005-09-13",
      "backdrop_path": "/e9n87p3Ax67spq3eUgLB6rjIEow.jpg",
      "original_language": "en",
      "id": 1911,
      "vote_average": 8.1,
      "overview": "Dr. Temperance Brennan and her colleagues at the Jeffersonian's Medico-Legal Lab assist Special Agent Seeley Booth with murder investigations when the remains are so badly decomposed, burned or destroyed that the standard identification methods are useless.",
      "poster_path": "/1bwF1daWnsEYYjbHXiEMdS587fR.jpg"
    },
    {
      "original_name": "The Umbrella Academy",
      "genre_ids": [
        35,
        18,
        10759,
        10765
      ],
      "name": "The Umbrella Academy",
      "popularity": 278.982,
      "origin_country": [
        "US"
      ],
      "vote_count": 3232,
      "first_air_date": "2019-02-15",
      "backdrop_path": "/qJxzjUjCpTPvDHldNnlbRC4OqEh.jpg",
      "original_language": "en",
      "id": 75006,
      "vote_average": 8.7,
      "overview": "A dysfunctional family of superheroes comes together to solve the mystery of their father's death, the threat of the apocalypse and more.",
      "poster_path": "/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg"
    },
    {
      "original_name": "The Flash",
      "genre_ids": [
        18,
        10765
      ],
      "name": "The Flash",
      "popularity": 275.263,
      "origin_country": [
        "US"
      ],
      "vote_count": 5999,
      "first_air_date": "2014-10-07",
      "backdrop_path": "/z59kJfcElR9eHO9rJbWp4qWMuee.jpg",
      "original_language": "en",
      "id": 60735,
      "vote_average": 7.5,
      "overview": "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \"meta-human\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash.",
      "poster_path": "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg"
    },
    {
      "original_name": "Law & Order: Special Victims Unit",
      "genre_ids": [
        80,
        18
      ],
      "name": "Law & Order: Special Victims Unit",
      "popularity": 272.305,
      "origin_country": [
        "US"
      ],
      "vote_count": 1797,
      "first_air_date": "1999-09-20",
      "backdrop_path": "/cD9PxbrdWYgL7MUpD9eOYuiSe2P.jpg",
      "original_language": "en",
      "id": 2734,
      "vote_average": 7.5,
      "overview": "In the criminal justice system, sexually-based offenses are considered especially heinous. In New York City, the dedicated detectives who investigate these vicious felonies are members of an elite squad known as the Special Victims Unit. These are their stories.",
      "poster_path": "/jDCgWVlejIo8sQYxw3Yf1cVQUIL.jpg"
    },
    {
      "original_name": "I Am...",
      "genre_ids": [
        18
      ],
      "name": "I Am...",
      "popularity": 258.682,
      "origin_country": [],
      "vote_count": 2,
      "first_air_date": "2019-07-23",
      "backdrop_path": null,
      "original_language": "en",
      "id": 91605,
      "vote_average": 4,
      "overview": "Each hour-long film follows a different women as they experience “moments that are emotionally raw, thought-provoking and utterly personal”.",
      "poster_path": "/oogunE22HDTcTxFakKQbwqfw1qo.jpg"
    },
    {
      "original_name": "The Mandalorian",
      "genre_ids": [
        10765,
        10759,
        37
      ],
      "name": "The Mandalorian",
      "popularity": 253.429,
      "origin_country": [
        "US"
      ],
      "vote_count": 1714,
      "first_air_date": "2019-11-12",
      "backdrop_path": "/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg",
      "original_language": "en",
      "id": 82856,
      "vote_average": 8.4,
      "overview": "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
      "poster_path": "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg"
    }
  ]
}"""

        const val onAirTVResponse = """{
  "page": 1,
  "total_results": 552,
  "total_pages": 28,
  "results": [
    {
      "original_name": "Fear the Walking Dead",
      "genre_ids": [
        10759,
        18
      ],
      "name": "Fear the Walking Dead",
      "popularity": 742.468,
      "origin_country": [
        "US"
      ],
      "vote_count": 2411,
      "first_air_date": "2015-08-23",
      "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
      "original_language": "en",
      "id": 62286,
      "vote_average": 7.4,
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg"
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "name": "The Walking Dead: World Beyond",
      "popularity": 505.253,
      "origin_country": [
        "US"
      ],
      "vote_count": 305,
      "first_air_date": "2020-10-04",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "original_language": "en",
      "id": 94305,
      "vote_average": 7.8,
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg"
    },
    {
      "original_name": "The Simpsons",
      "genre_ids": [
        16,
        35,
        18,
        10751
      ],
      "name": "The Simpsons",
      "popularity": 332.193,
      "origin_country": [
        "US"
      ],
      "vote_count": 5026,
      "first_air_date": "1989-12-16",
      "backdrop_path": "/adZ9ldSlkGfLfsHNbh37ZThCcgU.jpg",
      "original_language": "en",
      "id": 456,
      "vote_average": 7.7,
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "poster_path": "/qcr9bBY6MVeLzriKCmJOv1562uY.jpg"
    },
    {
      "original_name": "The Good Doctor",
      "genre_ids": [
        18
      ],
      "name": "The Good Doctor",
      "popularity": 302.957,
      "origin_country": [
        "US"
      ],
      "vote_count": 5263,
      "first_air_date": "2017-09-25",
      "backdrop_path": "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
      "original_language": "en",
      "id": 71712,
      "vote_average": 8.6,
      "overview": "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
      "poster_path": "/53P8oHo9cfOsgb1cLxBi4pFY0ja.jpg"
    },
    {
      "original_name": "The Mandalorian",
      "genre_ids": [
        10765,
        10759,
        37
      ],
      "name": "The Mandalorian",
      "popularity": 253.429,
      "origin_country": [
        "US"
      ],
      "vote_count": 1714,
      "first_air_date": "2019-11-12",
      "backdrop_path": "/o7qi2v4uWQ8bZ1tW3KI0Ztn2epk.jpg",
      "original_language": "en",
      "id": 82856,
      "vote_average": 8.4,
      "overview": "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
      "poster_path": "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg"
    },
    {
      "original_name": "We Are Who We Are",
      "genre_ids": [
        18
      ],
      "name": "We Are Who We Are",
      "popularity": 158.769,
      "origin_country": [
        "US"
      ],
      "vote_count": 14,
      "first_air_date": "2020-09-14",
      "backdrop_path": "/ioxGhd9jtJT8qGQHuhuodlqaGmX.jpg",
      "original_language": "en",
      "id": 88713,
      "vote_average": 7.2,
      "overview": "Two American kids who live on a U.S. military base in Italy explore friendship, first love, identity, and all the messy exhilaration and anguish of being a teenager.",
      "poster_path": "/33btSKKmjmc24hK9Vj1sRWQGfyh.jpg"
    },
    {
      "original_name": "Family Guy",
      "genre_ids": [
        16,
        35
      ],
      "name": "Family Guy",
      "popularity": 154.552,
      "origin_country": [
        "US"
      ],
      "vote_count": 2392,
      "first_air_date": "1999-01-31",
      "backdrop_path": "/4oE4vT4q0AD2cX3wcMBVzCsME8G.jpg",
      "original_language": "en",
      "id": 1434,
      "vote_average": 6.8,
      "overview": "Sick, twisted, politically incorrect and Freakin' Sweet animated series featuring the adventures of the dysfunctional Griffin family. Bumbling Peter and long-suffering Lois have three kids. Stewie (a brilliant but sadistic baby bent on killing his mother and taking over the world), Meg (the oldest, and is the most unpopular girl in town) and Chris (the middle kid, he's not very bright but has a passion for movies). The final member of the family is Brian - a talking dog and much more than a pet, he keeps Stewie in check whilst sipping Martinis and sorting through his own life issues.",
      "poster_path": "/q3E71oY6qgAEiw6YZIHDlHSLwer.jpg"
    },
    {
      "original_name": "Star Trek: Discovery",
      "genre_ids": [
        10759,
        10765
      ],
      "name": "Star Trek: Discovery",
      "popularity": 122.079,
      "origin_country": [
        "US"
      ],
      "vote_count": 811,
      "first_air_date": "2017-09-24",
      "backdrop_path": "/p3McpsDNTNmpbkNBKdNxOFZJeKX.jpg",
      "original_language": "en",
      "id": 67198,
      "vote_average": 7,
      "overview": "Follow the voyages of Starfleet on their missions to discover new worlds and new life forms, and one Starfleet officer who must learn that to truly understand all things alien, you must first understand yourself.",
      "poster_path": "/98RYSYsRNKWgrAAFBn0WfploUG7.jpg"
    },
    {
      "original_name": "Patria",
      "genre_ids": [
        80,
        18
      ],
      "name": "Patria",
      "popularity": 98.61,
      "origin_country": [
        "ES"
      ],
      "vote_count": 46,
      "first_air_date": "2020-09-27",
      "backdrop_path": "/2FdeV6TxG02XL5nBF9NUvoi1BkW.jpg",
      "original_language": "es",
      "id": 85964,
      "vote_average": 6.4,
      "overview": "A look at the impact of Spain’s Basque conflict on ordinary people on both sides, such as the widow of a man killed by the ETA who returns to her home village after the 2011 ceasefire between the separatist group and the Spanish government, and her former intimate friend, the mother of a jailed terrorist.",
      "poster_path": "/6VxzHeOd237QMhnmVYALeB26bn4.jpg"
    },
    {
      "original_name": "デジモンアドベンチャー：",
      "genre_ids": [
        16,
        35,
        10759,
        10765
      ],
      "name": "Digimon Adventure:",
      "popularity": 89.008,
      "origin_country": [
        "JP"
      ],
      "vote_count": 359,
      "first_air_date": "2020-04-05",
      "backdrop_path": "/xsEMAdrDprq3Ldre56Rm0zqbfCA.jpg",
      "original_language": "ja",
      "id": 98034,
      "vote_average": 7.4,
      "overview": "The new anime will take place in 2020 and will feature an all-new story centering on Taichi Yagami when he is in his fifth year in elementary school. His partner is Agumon. The story begins in Tokyo when a large-scale network malfunction occurs. Taichi is preparing for his weekend summer camping trip when the incident happens. Taichi's mother and his younger sister Hikari get stuck on a train that won't stop moving, and Taichi heads to Shibuya in order to help them. However, on his way there, he encounters a strange phenomenon and sweeps him up into the Digital World along with the other DigiDestined.",
      "poster_path": "/7Qspx2eFX0uBSQLLlAKnYrjZgse.jpg"
    },
    {
      "original_name": "ポケットモンスター",
      "genre_ids": [
        16,
        10759,
        10765
      ],
      "name": "Pokémon",
      "popularity": 88.938,
      "origin_country": [
        "JP"
      ],
      "vote_count": 422,
      "first_air_date": "1997-04-01",
      "backdrop_path": "/tvjCdVRkaaab2ezM9BctkAOXeyW.jpg",
      "original_language": "ja",
      "id": 60572,
      "vote_average": 6.7,
      "overview": "Join Satoshi, accompanied by his partner Pikachu, as he travels through many regions, meets new friends and faces new challenges on his quest to become a Pokémon Master.",
      "poster_path": "/rOuGm07PxBhEsK9TaGPRQVJQm1X.jpg"
    },
    {
      "original_name": "런닝맨",
      "genre_ids": [
        35,
        10764
      ],
      "name": "Running Man",
      "popularity": 85.711,
      "origin_country": [
        "KR"
      ],
      "vote_count": 54,
      "first_air_date": "2010-07-11",
      "backdrop_path": "/rHuXgDmrv4vMKgQZ6pu2E2iLJnM.jpg",
      "original_language": "ko",
      "id": 33238,
      "vote_average": 8.1,
      "overview": "A landmark representing Korea, how much do you know?  Korea's representative landmark to let the running man know directly!  The best performers of Korea gathered there! Solve the missions in various places and get out of there until the next morning through game!  Through constant confrontation and urgent confrontation, we will reveal the hidden behind-the-scenes of Korean landmarks!",
      "poster_path": "/2Wmmu1MkqxJ48J7aySET9EKEjXz.jpg"
    },
    {
      "original_name": "The Graham Norton Show",
      "genre_ids": [
        35,
        10767
      ],
      "name": "The Graham Norton Show",
      "popularity": 84.657,
      "origin_country": [
        "GB"
      ],
      "vote_count": 144,
      "first_air_date": "2007-02-22",
      "backdrop_path": "/2pJYis3LUEgFC3UErTQVgmUV1hN.jpg",
      "original_language": "en",
      "id": 1220,
      "vote_average": 7,
      "overview": "Each week celebrity guests join Irish comedian Graham Norton to discuss what's being going on around the world that week. The guests poke fun and share their opinions on the main news stories. Graham is often joined by a band or artist to play the show out.",
      "poster_path": "/vrbqaBXB8AALynQzpWz6JdCPEJS.jpg"
    },
    {
      "original_name": "Young Sheldon",
      "genre_ids": [
        35
      ],
      "name": "Young Sheldon",
      "popularity": 82.501,
      "origin_country": [
        "US"
      ],
      "vote_count": 846,
      "first_air_date": "2017-09-25",
      "backdrop_path": "/ko0Gynw9pMRdcScCjwGYfhJlfWh.jpg",
      "original_language": "en",
      "id": 71728,
      "vote_average": 7.9,
      "overview": "The early life of child genius Sheldon Cooper, later seen in The Big Bang Theory.",
      "poster_path": "/h1XBglOtfAR7lZVIusPd7BLgkHu.jpg"
    },
    {
      "original_name": "Bob's Burgers",
      "genre_ids": [
        16,
        35
      ],
      "name": "Bob's Burgers",
      "popularity": 79.924,
      "origin_country": [
        "US"
      ],
      "vote_count": 391,
      "first_air_date": "2011-01-09",
      "backdrop_path": "/rUABoTPO0OLq2wlHiFWezxpV6fo.jpg",
      "original_language": "en",
      "id": 32726,
      "vote_average": 7.8,
      "overview": "Bob's Burgers follows a third-generation restaurateur, Bob, as he runs Bob's Burgers with the help of his wife and their three kids. Bob and his quirky family have big ideas about burgers, but fall short on service and sophistication. Despite the greasy counters, lousy location and a dearth of customers, Bob and his family are determined to make Bob's Burgers \"grand re-re-re-opening\" a success.",
      "poster_path": "/joPhJJblNtFDOpp6zQlx0xJWsmM.jpg"
    },
    {
      "original_name": "The Undoing",
      "genre_ids": [
        18
      ],
      "name": "The Undoing",
      "popularity": 72.729,
      "origin_country": [
        "US"
      ],
      "vote_count": 10,
      "first_air_date": "2020-10-25",
      "backdrop_path": "/tJjwEzTglpxoWJXwEbZlKLppMhr.jpg",
      "original_language": "en",
      "id": 83851,
      "vote_average": 7.7,
      "overview": "Grace Fraser is a successful therapist on the brink of publishing her first book with a devoted husband and young son who attends an elite private school in New York City. Weeks before her book is published, a chasm opens in her life: a violent death, a missing husband, and, in the place of a man Grace thought she knew, only a chain of terrible revelations.",
      "poster_path": "/3tDbJxobPN3EI2bBebL6zmusmw5.jpg"
    },
    {
      "original_name": "Suburra - La serie",
      "genre_ids": [
        80,
        18
      ],
      "name": "Suburra: Blood on Rome",
      "popularity": 72.528,
      "origin_country": [
        "IT"
      ],
      "vote_count": 44,
      "first_air_date": "2017-10-06",
      "backdrop_path": "/2MLA8lpII4CVNQV7pYGyZRRip67.jpg",
      "original_language": "it",
      "id": 73671,
      "vote_average": 7.5,
      "overview": "In 2008, a fight over land in a seaside town near Rome spirals into a deadly battle between organized crime, corrupt politicians and the Vatican.",
      "poster_path": "/ri1WF0vxDfJcUW8iNUOu8OsmVeJ.jpg"
    },
    {
      "original_name": "The Spanish Princess",
      "id": 86428,
      "name": "The Spanish Princess",
      "popularity": 60.16,
      "vote_count": 236,
      "vote_average": 7.6,
      "first_air_date": "2019-05-05",
      "poster_path": "/4Ec0jIkctpiyKuywPSRj4Pd2jEl.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/eqOAW52GkgbMDGIzpqlz8zwTIiO.jpg",
      "overview": "The beautiful Spanish princess, Catherine of Aragon, navigates the royal lineage of England with an eye on the throne.",
      "origin_country": [
        "US"
      ]
    },
    {
      "original_name": "Superstore",
      "id": 62649,
      "name": "Superstore",
      "popularity": 58.058,
      "vote_count": 162,
      "vote_average": 7,
      "first_air_date": "2015-11-30",
      "poster_path": "/maBJkaBM4UqAttn9UkLCfZEVEfk.jpg",
      "genre_ids": [
        35
      ],
      "original_language": "en",
      "backdrop_path": "/zUCGshcuj2jg8qgHAvgKWq3xan8.jpg",
      "overview": "A hilarious workplace comedy about a unique family of employees at a super-sized mega store. From the bright-eyed newbies and the seen-it-all veterans to the clueless summer hires and the in-it-for-life managers, together they hilariously tackle the day-to-day grind of rabid bargain hunters, riot-causing sales and nap-worthy training sessions.",
      "origin_country": [
        "US"
      ]
    },
    {
      "original_name": "Real Time with Bill Maher",
      "genre_ids": [
        35,
        10767
      ],
      "name": "Real Time with Bill Maher",
      "popularity": 52.428,
      "origin_country": [
        "US"
      ],
      "vote_count": 125,
      "first_air_date": "2003-02-21",
      "backdrop_path": "/cs4wxElH1XPgRLFq1FOtIFpeKqz.jpg",
      "original_language": "en",
      "id": 4419,
      "vote_average": 6.1,
      "overview": "Each week Bill Maher surrounds himself with a panel of guests which include politicians, actors, comedians, musicians and the like to discuss what's going on in the world.",
      "poster_path": "/c3EurMWJu1hXKUeJVvLIoJaN26j.jpg"
    }
  ]
}"""

        const val recommendationTVShowsResponse = """{
  "page": 1,
  "results": [
    {
      "backdrop_path": "/jCvwvSH5i9bLaql1rR21j9U9LS.jpg",
      "first_air_date": "2014-09-12",
      "genre_ids": [
        9648,
        10759,
        18
      ],
      "id": 61345,
      "name": "Z Nation",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Z Nation",
      "overview": "Three years after the zombie virus has gutted the country, a team of everyday heroes must transport the only known survivor of the plague from New York to California, where the last functioning viral lab waits for his blood.",
      "poster_path": "/gXfeDMkEcHoYBvtkbU11g3F81b.jpg",
      "vote_average": 6.8,
      "vote_count": 402,
      "networks": [
        {
          "id": 77,
          "logo": {
            "path": "/iYfrkobwDhTOFJ4AXYPSLIEeaAT.png",
            "aspect_ratio": 4.081632653061225
          },
          "name": "Syfy",
          "origin_country": "US"
        }
      ],
      "popularity": 73.713
    },
    {
      "backdrop_path": "/wXXaPMgrv96NkH8KD1TMdS2d7iq.jpg",
      "first_air_date": "2010-10-31",
      "genre_ids": [
        10759,
        18,
        10765
      ],
      "id": 1402,
      "name": "The Walking Dead",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "The Walking Dead",
      "overview": "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
      "poster_path": "/qgjP2OrrX9gc6M270xdPnEmE9tC.jpg",
      "vote_average": 7.9,
      "vote_count": 8642,
      "networks": [
        {
          "id": 174,
          "logo": {
            "path": "/pmvRmATOCaDykE6JrVoeYxlFHw3.png",
            "aspect_ratio": 1.768
          },
          "name": "AMC",
          "origin_country": "US"
        }
      ],
      "popularity": 307.258
    },
    {
      "backdrop_path": "/aNXKB65a6hTlyxJOi6Xt2vzVqAl.jpg",
      "first_air_date": "2015-05-14",
      "genre_ids": [
        18,
        9648,
        10765
      ],
      "id": 53425,
      "name": "Wayward Pines",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Wayward Pines",
      "overview": "Imagine the perfect American town... beautiful homes, manicured lawns, children playing safely in the streets. Now imagine never being able to leave. You have no communication with the outside world. You think you're going insane. You must be in Wayward Pines.",
      "poster_path": "/mK9MowBCPEn7RbsDXqN0MxyPc4.jpg",
      "vote_average": 6.7,
      "vote_count": 423,
      "networks": [
        {
          "id": 19,
          "logo": {
            "path": "/1DSpHrWyOORkL9N2QHX7Adt31mQ.png",
            "aspect_ratio": 2.325581395348837
          },
          "name": "FOX",
          "origin_country": "US"
        }
      ],
      "popularity": 23.628
    },
    {
      "backdrop_path": "/i1wJZlBdQkEv9RGirvHti7hu86m.jpg",
      "first_air_date": "2014-06-22",
      "genre_ids": [
        10759,
        18,
        10765
      ],
      "id": 60802,
      "name": "The Last Ship",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "The Last Ship",
      "overview": "Their mission is simple: Find a cure. Stop the virus. Save the world. When a global pandemic wipes out eighty percent of the planet's population, the crew of a lone naval destroyer must find a way to pull humanity from the brink of extinction.",
      "poster_path": "/43pAddeD10rllMQMGN7ucuOi4NI.jpg",
      "vote_average": 6.3,
      "vote_count": 539,
      "networks": [
        {
          "id": 41,
          "logo": {
            "path": "/6ISsKwa2XUhSC6oBtHZjYf6xFqv.png",
            "aspect_ratio": 1
          },
          "name": "TNT",
          "origin_country": "US"
        }
      ],
      "popularity": 52.675
    },
    {
      "backdrop_path": "/cVTXRNJps13QeVKDJ7NhDaHMK0v.jpg",
      "first_air_date": "2016-05-22",
      "genre_ids": [
        9648,
        18,
        10765
      ],
      "id": 64230,
      "name": "Preacher",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Preacher",
      "overview": "A preacher sets out on a mission to make the almighty himself confess his sin of abandoning the world. With his best friend Cassidy, an alcoholic Irish vampire, his love Tulip, a red blooded gun towing Texan, and the power of genesis, an unholy child born from an angel and a devil, Jesse gives up everything to set the world straight with its creator.",
      "poster_path": "/ey1WQajA25E5sFGHSApcqSWUSEc.jpg",
      "vote_average": 7.4,
      "vote_count": 648,
      "networks": [
        {
          "id": 174,
          "logo": {
            "path": "/alqLicR1ZMHMaZGP3xRQxn9sq7p.png",
            "aspect_ratio": 1.774011299435028
          },
          "name": "AMC",
          "origin_country": "US"
        }
      ],
      "popularity": 37.895
    },
    {
      "backdrop_path": "/gZQxmxvm5D54o8t0RMgPSihv2K.jpg",
      "first_air_date": "2016-01-14",
      "genre_ids": [
        878,
        18
      ],
      "id": 62858,
      "name": "Colony",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Colony",
      "overview": "In the near future a family must make difficult decisions as they balance staying together with trying to survive. They live in Los Angeles, which has been occupied by a force of outside intruders.  While some people have chosen to collaborate with the authorities and benefit from the new order, others have rebelled — and suffer the consequences.",
      "poster_path": "/qfS0mp22XfTig2EK3jWKiyx7kNy.jpg",
      "vote_average": 6.8,
      "vote_count": 379,
      "networks": [
        {
          "id": 30,
          "logo": {
            "path": "/g1e0H0Ka97IG5SyInMXdJkHGKiH.png",
            "aspect_ratio": 2.278755074424899
          },
          "name": "USA Network",
          "origin_country": "US"
        }
      ],
      "popularity": 28.996
    },
    {
      "backdrop_path": "/qpZ7dcM5yDyBDP4JSQr9LRUIBRm.jpg",
      "first_air_date": "2004-09-22",
      "genre_ids": [
        10759,
        9648
      ],
      "id": 4607,
      "name": "Lost",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Lost",
      "overview": "Stripped of everything, the survivors of a horrific plane crash  must work together to stay alive. But the island holds many secrets.",
      "poster_path": "/og6S0aTZU6YUJAbqxeKjCa3kY1E.jpg",
      "vote_average": 7.9,
      "vote_count": 1967,
      "networks": [
        {
          "id": 2,
          "logo": {
            "path": "/ndAvF4JLsliGreX87jAc9GdjmJY.png",
            "aspect_ratio": 1
          },
          "name": "ABC",
          "origin_country": "US"
        }
      ],
      "popularity": 112.72
    },
    {
      "backdrop_path": "/q8FS5ZtkV98MYbUR1wJRyvVJGav.jpg",
      "first_air_date": "2015-03-01",
      "genre_ids": [
        18,
        35
      ],
      "id": 61888,
      "name": "The Last Man on Earth",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "The Last Man on Earth",
      "overview": "The year is 2022, and after an unlikely event, only one man is left on earth: Phil Miller, who used to be just an average guy who loved his family and hated his job at the bank. Now, in his RV, Phil searches the country for other survivors.",
      "poster_path": "/9TcvdOIBEnIDXbLvFUKluRDa3tZ.jpg",
      "vote_average": 6.6,
      "vote_count": 363,
      "networks": [
        {
          "id": 19,
          "logo": {
            "path": "/1DSpHrWyOORkL9N2QHX7Adt31mQ.png",
            "aspect_ratio": 2.325581395348837
          },
          "name": "FOX",
          "origin_country": "US"
        }
      ],
      "popularity": 27.526
    },
    {
      "backdrop_path": "/qXm5j4jxl69kp7bO6O8VbDriMPA.jpg",
      "first_air_date": "2014-06-29",
      "genre_ids": [
        10765,
        18
      ],
      "id": 54344,
      "name": "The Leftovers",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "The Leftovers",
      "overview": "When 2% of the world's population abruptly disappears without explanation, the world struggles to understand just what they're supposed to do about it. The drama series 'The Leftovers' is the story of the people who didn't make the cut.\n\nBased on the bestselling novel by Tom Perrotta, 'The Leftovers' follows Kevin Garvey, a father of two and the chief of police in a small New York suburb, as he tries to maintain some semblance of normalcy when the notion no longer applies.",
      "poster_path": "/ri8xr223xBb2TeHX3GKypvQPV2B.jpg",
      "vote_average": 7.7,
      "vote_count": 497,
      "networks": [
        {
          "id": 49,
          "logo": {
            "path": "/tuomPhY2UtuPTqqFnKMVHvSb724.png",
            "aspect_ratio": 2.425287356321839
          },
          "name": "HBO",
          "origin_country": "US"
        }
      ],
      "popularity": 39.859
    },
    {
      "backdrop_path": "/AlqSvfI6bmxh31iaJTgjNSemF3D.jpg",
      "first_air_date": "2015-02-08",
      "genre_ids": [
        35,
        80,
        18
      ],
      "id": 60059,
      "name": "Better Call Saul",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Better Call Saul",
      "overview": "Six years before Saul Goodman meets Walter White. We meet him when the man who will become Saul Goodman is known as Jimmy McGill, a small-time lawyer searching for his destiny, and, more immediately, hustling to make ends meet. Working alongside, and, often, against Jimmy, is “fixer” Mike Ehrmantraut. The series tracks Jimmy’s transformation into Saul Goodman, the man who puts “criminal” in “criminal lawyer\".",
      "poster_path": "/cU0kAjGjA6d2XjBzJMUIEVKiGDb.jpg",
      "vote_average": 8.3,
      "vote_count": 1885,
      "networks": [
        {
          "id": 174,
          "logo": {
            "path": "/alqLicR1ZMHMaZGP3xRQxn9sq7p.png",
            "aspect_ratio": 1.774011299435028
          },
          "name": "AMC",
          "origin_country": "US"
        }
      ],
      "popularity": 88.996
    },
    {
      "backdrop_path": "/lFlO6ZWC3yZHDvfiuy3Ni1XuIqu.jpg",
      "first_air_date": "2012-09-17",
      "genre_ids": [
        28,
        878,
        18,
        12
      ],
      "id": 1410,
      "name": "Revolution",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Revolution",
      "overview": "One day, electricity just stopped working and the world was suddenly thrust back into the dark ages. Now, 15 years later, a young woman's life is dramatically changed when a local militia arrives and kills her father, who mysteriously—and unbeknownst to her—had something to do with the blackout. An unlikely group sets out off on a daring journey to find answers about the past in the hopes of reclaiming the future.",
      "poster_path": "/pNyg4V6kuDstSSxk6Q9GgaBn4B8.jpg",
      "vote_average": 6.3,
      "vote_count": 312,
      "networks": [
        {
          "id": 6,
          "logo": {
            "path": "/o3OedEP0f9mfZr33jz2BfXOUK5.png",
            "aspect_ratio": 4.083582089552239
          },
          "name": "NBC",
          "origin_country": "US"
        }
      ],
      "popularity": 12.741
    },
    {
      "backdrop_path": "/4YnAZ1KO9goeS4c06VO7HXiO3oO.jpg",
      "first_air_date": "2014-07-13",
      "genre_ids": [
        878,
        18,
        9648
      ],
      "id": 47640,
      "name": "The Strain",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "The Strain",
      "overview": "A high concept thriller that tells the story of Dr. Ephraim Goodweather, the head of the Center for Disease Control Canary Team in New York City. He and his team are called upon to investigate a mysterious viral outbreak with hallmarks of an ancient and evil strain of vampirism. As the strain spreads, Eph, his team, and an assembly of everyday New Yorkers, wage war for the fate of humanity itself.",
      "poster_path": "/2BWErT9QcADpf2G4BZ769eSnFTP.jpg",
      "vote_average": 7.3,
      "vote_count": 933,
      "networks": [
        {
          "id": 88,
          "logo": {
            "path": "/aexGjtcs42DgRtZh7zOxayiry4J.png",
            "aspect_ratio": 1.677852348993289
          },
          "name": "FX",
          "origin_country": "US"
        }
      ],
      "popularity": 58.533
    },
    {
      "backdrop_path": "/xcJLhsFGVC4LVvucSqVXks2mnUJ.jpg",
      "first_air_date": "2014-04-15",
      "genre_ids": [
        80,
        18
      ],
      "id": 60622,
      "name": "Fargo",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Fargo",
      "overview": "A close-knit anthology series dealing with stories involving malice, violence and murder based in and around Minnesota.",
      "poster_path": "/9ZIhl17uFlXCNUputSEDHwZYIEJ.jpg",
      "vote_average": 8.3,
      "vote_count": 1375,
      "networks": [
        {
          "id": 88,
          "logo": {
            "path": "/aexGjtcs42DgRtZh7zOxayiry4J.png",
            "aspect_ratio": 1.677852348993289
          },
          "name": "FX",
          "origin_country": "US"
        }
      ],
      "popularity": 61.437
    },
    {
      "backdrop_path": "/a7fRjgWc37Ml6bSlWoNaG7wbaqP.jpg",
      "first_air_date": "2006-10-01",
      "genre_ids": [
        80,
        18,
        9648
      ],
      "id": 1405,
      "name": "Dexter",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Dexter",
      "overview": "Dexter Morgan, a blood spatter pattern analyst for the Miami Metro Police also leads a secret life as a serial killer, hunting down criminals who have slipped through the cracks of justice.",
      "poster_path": "/p4rx3FW14Ayx1dLHJQBqDGw9YiX.jpg",
      "vote_average": 8.1,
      "vote_count": 2030,
      "networks": [
        {
          "id": 67,
          "logo": {
            "path": "/Allse9kbjiP6ExaQrnSpIhkurEi.png",
            "aspect_ratio": 2.42080378250591
          },
          "name": "Showtime",
          "origin_country": "US"
        }
      ],
      "popularity": 97.392
    },
    {
      "backdrop_path": "/qsnXwGS7KBbX4JLqHvICngtR8qg.jpg",
      "first_air_date": "2015-04-10",
      "genre_ids": [
        28,
        80
      ],
      "id": 61889,
      "name": "Marvel's Daredevil",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Marvel's Daredevil",
      "overview": "Lawyer-by-day Matt Murdock uses his heightened senses from being blinded as a young boy to fight crime at night on the streets of Hell’s Kitchen as Daredevil.",
      "poster_path": "/QWbPaDxiB6LW2LjASknzYBvjMj.jpg",
      "vote_average": 7.9,
      "vote_count": 2104,
      "networks": [
        {
          "id": 213,
          "logo": {
            "path": "/wwemzKWzjKYJFfCeiB57q3r4Bcm.png",
            "aspect_ratio": 3.73134328358209
          },
          "name": "Netflix",
          "origin_country": ""
        }
      ],
      "popularity": 73.113
    },
    {
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "first_air_date": "2020-10-04",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "id": 94305,
      "name": "The Walking Dead: World Beyond",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "The Walking Dead: World Beyond",
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world receive a message that inspires them to leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg",
      "vote_average": 7.8,
      "vote_count": 421,
      "networks": [
        {
          "id": 174,
          "logo": {
            "path": "/alqLicR1ZMHMaZGP3xRQxn9sq7p.png",
            "aspect_ratio": 1.774011299435028
          },
          "name": "AMC",
          "origin_country": "US"
        }
      ],
      "popularity": 367.83
    },
    {
      "backdrop_path": "/4DgAubucJP6y2yX2Yx4CLEgdIPA.jpg",
      "first_air_date": "2006-09-25",
      "genre_ids": [
        10765,
        18
      ],
      "id": 1639,
      "name": "Heroes",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Heroes",
      "overview": "Common people discover that they have super powers. Their lives intertwine as a devastating event must be prevented.",
      "poster_path": "/lf0TcOkheYUZKpeh7c8lqJHNk5O.jpg",
      "vote_average": 7.3,
      "vote_count": 1008,
      "networks": [
        {
          "id": 6,
          "logo": {
            "path": "/o3OedEP0f9mfZr33jz2BfXOUK5.png",
            "aspect_ratio": 4.083582089552239
          },
          "name": "NBC",
          "origin_country": "US"
        }
      ],
      "popularity": 137.266
    },
    {
      "backdrop_path": "/s9kC7NkVolxkG2gPHvWLFj0FIk4.jpg",
      "first_air_date": "2011-06-19",
      "genre_ids": [
        10759,
        10765
      ],
      "id": 34967,
      "name": "Falling Skies",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Falling Skies",
      "overview": "Falling Skies opens in the chaotic aftermath of an alien attack that has left most of the world completely incapacitated. In the six months since the initial invasion, the few survivors have banded together outside major cities to begin the difficult task of fighting back. Each day is a test of survival as citizen soldiers work to protect the people in their care while also engaging in an insurgency campaign against the occupying alien force.",
      "poster_path": "/i2ctkUM209mRyvWRrERXCbSarKr.jpg",
      "vote_average": 7,
      "vote_count": 711,
      "networks": [
        {
          "id": 41,
          "logo": {
            "path": "/6ISsKwa2XUhSC6oBtHZjYf6xFqv.png",
            "aspect_ratio": 1
          },
          "name": "TNT",
          "origin_country": "US"
        }
      ],
      "popularity": 38.127
    },
    {
      "backdrop_path": "/57JFnZqpG5OLgHdZY7Zf9dKcZ5U.jpg",
      "first_air_date": "2015-11-20",
      "genre_ids": [
        10765,
        18
      ],
      "id": 38472,
      "name": "Marvel's Jessica Jones",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Marvel's Jessica Jones",
      "overview": "After a tragic ending to her short-lived super hero stint, Jessica Jones is rebuilding her personal life and career as a detective who gets pulled into cases involving people with extraordinary abilities in New York City.",
      "poster_path": "/wkFdanlAaV39sSXeslImfRUj3jQ.jpg",
      "vote_average": 7.6,
      "vote_count": 1256,
      "networks": [
        {
          "id": 213,
          "logo": {
            "path": "/wwemzKWzjKYJFfCeiB57q3r4Bcm.png",
            "aspect_ratio": 3.73134328358209
          },
          "name": "Netflix",
          "origin_country": ""
        }
      ],
      "popularity": 76.556
    },
    {
      "backdrop_path": "/2Ahm0YjLNQKuzSf9LOkHrXk8qIE.jpg",
      "first_air_date": "2014-01-12",
      "genre_ids": [
        18
      ],
      "id": 46648,
      "name": "True Detective",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "True Detective",
      "overview": "An American anthology police detective series utilizing multiple timelines in which investigations seem to unearth personal and professional secrets of those involved, both within or outside the law.",
      "poster_path": "/aowr4xpLP5sRCL50TkuADomJ98T.jpg",
      "vote_average": 8.3,
      "vote_count": 1601,
      "networks": [
        {
          "id": 49,
          "logo": {
            "path": "/tuomPhY2UtuPTqqFnKMVHvSb724.png",
            "aspect_ratio": 2.425287356321839
          },
          "name": "HBO",
          "origin_country": "US"
        }
      ],
      "popularity": 40.87
    }
  ],
  "total_pages": 2,
  "total_results": 40
}"""

        const val similarTVShowsResponse = """{
  "page": 1,
  "results": [
    {
      "original_name": "The Walking Dead",
      "id": 1402,
      "name": "The Walking Dead",
      "vote_count": 8642,
      "vote_average": 7.9,
      "first_air_date": "2010-10-31",
      "poster_path": "/qgjP2OrrX9gc6M270xdPnEmE9tC.jpg",
      "genre_ids": [
        10759,
        18,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/wXXaPMgrv96NkH8KD1TMdS2d7iq.jpg",
      "overview": "Sheriff's deputy Rick Grimes awakens from a coma to find a post-apocalyptic world dominated by flesh-eating zombies. He sets out to find his family and encounters many other survivors along the way.",
      "origin_country": [
        "US"
      ],
      "popularity": 307.258
    },
    {
      "original_name": "学園黙示録 HIGHSCHOOL OF THE DEAD",
      "id": 56998,
      "name": "Highschool of the Dead",
      "vote_count": 332,
      "vote_average": 8.4,
      "first_air_date": "2010-07-05",
      "poster_path": "/wf3bBKp1RgCJjyCBLfEAbZjVr31.jpg",
      "genre_ids": [
        10759,
        16,
        18
      ],
      "original_language": "ja",
      "backdrop_path": "/ivWNDYPxDMjc82ajeSPuyq70hu9.jpg",
      "overview": "When the world is struck by a deadly pandemic that turns humans into zombies, Takashi Komuro and several of his classmates at Fujimi High School try to survive the apocalypse.",
      "origin_country": [
        "JP"
      ],
      "popularity": 32.985
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "id": 94305,
      "name": "The Walking Dead: World Beyond",
      "vote_count": 421,
      "vote_average": 7.8,
      "first_air_date": "2020-10-04",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "original_language": "en",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world receive a message that inspires them to leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "origin_country": [
        "US"
      ],
      "popularity": 367.83
    },
    {
      "backdrop_path": "/aFmqXViWzIKmhR8Cy4QDqPU6pIL.jpg",
      "first_air_date": "2019-01-25",
      "genre_ids": [
        18,
        10759,
        9648
      ],
      "id": 70593,
      "name": "Kingdom",
      "origin_country": [
        "KR"
      ],
      "original_language": "ko",
      "original_name": "킹덤",
      "overview": "In this zombie thriller set in Korea's medieval Joseon dynasty, a crown prince is sent on a suicide mission to investigate a mysterious outbreak.",
      "poster_path": "/AsICtiVtz4icMQQRwDvOzfaTzjK.jpg",
      "vote_average": 8.2,
      "vote_count": 353,
      "popularity": 32.45
    },
    {
      "original_name": "Z Nation",
      "id": 61345,
      "name": "Z Nation",
      "vote_count": 402,
      "vote_average": 6.8,
      "first_air_date": "2014-09-12",
      "poster_path": "/gXfeDMkEcHoYBvtkbU11g3F81b.jpg",
      "genre_ids": [
        9648,
        10759,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/jCvwvSH5i9bLaql1rR21j9U9LS.jpg",
      "overview": "Three years after the zombie virus has gutted the country, a team of everyday heroes must transport the only known survivor of the plague from New York to California, where the last functioning viral lab waits for his blood.",
      "origin_country": [
        "US"
      ],
      "popularity": 73.713
    },
    {
      "original_name": "デッドマン・ワンダーランド",
      "id": 42503,
      "name": "Deadman Wonderland",
      "vote_count": 172,
      "vote_average": 7.6,
      "first_air_date": "2011-04-17",
      "poster_path": "/m12JbXsDdB35RGAC1g3ImNQT4h5.jpg",
      "genre_ids": [
        16,
        10759,
        10765
      ],
      "original_language": "ja",
      "backdrop_path": "/hKkybBfYLM9aTUCQBL8fwxC0TwF.jpg",
      "overview": "Ganta is the only survivor after a mysterious man in red slaughters a classroom full of teenagers. He's framed for the carnage, sentenced to die, and locked away in the most twisted prison ever built: Deadman Wonderland. And then it gets worse.",
      "origin_country": [
        "JP"
      ],
      "popularity": 27.549
    },
    {
      "original_name": "The Last Ship",
      "id": 60802,
      "name": "The Last Ship",
      "vote_count": 539,
      "vote_average": 6.3,
      "first_air_date": "2014-06-22",
      "poster_path": "/43pAddeD10rllMQMGN7ucuOi4NI.jpg",
      "genre_ids": [
        18,
        10759,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/i1wJZlBdQkEv9RGirvHti7hu86m.jpg",
      "overview": "Their mission is simple: Find a cure. Stop the virus. Save the world. When a global pandemic wipes out eighty percent of the planet's population, the crew of a lone naval destroyer must find a way to pull humanity from the brink of extinction.",
      "origin_country": [
        "US"
      ],
      "popularity": 52.675
    },
    {
      "backdrop_path": "/ptiSlEK0UhqUGQxeOlS3rn5FBSn.jpg",
      "first_air_date": "2015-07-12",
      "genre_ids": [
        16,
        10765,
        18
      ],
      "id": 62773,
      "name": "God Eater",
      "origin_country": [
        "JP"
      ],
      "original_language": "ja",
      "original_name": "ゴッドイーター",
      "overview": "In the early 2050s, unknown life forms called “Oracle cells” begin their uncontrolled consumption of all life on Earth. Their ravenous appetite and remarkable adaptability earn them first dread, then awe, and finally the name “aragami”. In the face of an enemy completely immune to conventional weapons, urban civilization collapses, and each day humanity is driven further and further toward extinction. One single ray of hope remains for humanity. Following the development of “God Arcs”—living weapons which incorporate Oracle cells—their wielders are organized into an elite force.",
      "poster_path": "/ag4rXL4ZM6YAsWaI4BecLHWIqel.jpg",
      "vote_average": 6.6,
      "vote_count": 28,
      "popularity": 10.593
    },
    {
      "backdrop_path": "/oqFLhmwCpUyTxLWiMgIBt8VvbnT.jpg",
      "first_air_date": "2005-10-04",
      "genre_ids": [
        16,
        18,
        9648,
        10765
      ],
      "id": 8974,
      "name": "Hell Girl",
      "origin_country": [
        "JP"
      ],
      "original_language": "ja",
      "original_name": "地獄少女",
      "overview": "Hell Girl, also known as Jigoku Shōjo: Girl from Hell, is an anime series produced by Aniplex and Studio Deen. It focuses on the existence of a supernatural system that allows people to take revenge by having other people sent to Hell via the services of the mysterious titular character and her assistants who implement this system. Revenge, injustice, hatred, and the nature of human emotions are common themes throughout the series.\n\nIt premiered across Japan on numerous television stations, including Animax, Tokyo MX, MBS and others, between October 4, 2005 and April 4, 2006. Following the success of the first season, the series was followed soon after into a second, Jigoku Shōjo Futakomori, which premiered October 7, 2006 across Japan on Animax. A live-action television series adaptation started airing in Japan on Nippon Television from November 4, 2006. A third season of the anime, further continuing the series, was first announced on the mobile version of the series' official website Jigoku Tsūshin. The official title of the third season was announced to be Jigoku Shōjo Mitsuganae. and began airing on Japanese TV October 4, 2008.",
      "poster_path": "/58IhjAFrHSTN0CQFlBbQxdXwxLe.jpg",
      "vote_average": 8.2,
      "vote_count": 97,
      "popularity": 24.512
    },
    {
      "backdrop_path": "/uzp513qTcHsAavlCJ58x5d73bzy.jpg",
      "first_air_date": "2017-07-07",
      "genre_ids": [
        10759,
        16,
        10765
      ],
      "id": 72636,
      "name": "Made In Abyss",
      "origin_country": [
        "JP"
      ],
      "original_language": "ja",
      "original_name": "メイドインアビス",
      "overview": "The “Abyss” is the last unexplored region, an enormous and treacherous cave system filled with ancient relics and strange creatures. Only the bravest of adventurers can traverse its depths, earning them the nickname, “Cave Raiders.” Within the depths of the Abyss, a girl named Riko stumbles upon a robot who looks like a young boy. Tantalized by the Abyss, Riko and her new friend descend into uncharted territory to unlock its mysteries, but what lies in wait for them in the darkness?",
      "poster_path": "/uVK3H8CgtrVgySFpdImvNXkN7RK.jpg",
      "vote_average": 8.8,
      "vote_count": 127,
      "popularity": 31.316
    },
    {
      "original_name": "The Stand",
      "id": 9045,
      "name": "The Stand",
      "vote_count": 270,
      "vote_average": 6.8,
      "first_air_date": "1994-05-08",
      "poster_path": "/o4Gb63aXRh5mMqGifYACixnxGkQ.jpg",
      "genre_ids": [
        10765,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/2CfzWJCaTxlddUW2JzyhoRn1ZzC.jpg",
      "overview": "After a deadly plague kills most of the world's population, the remaining survivors split into two groups - one led by a benevolent elder and the other by a maleficent being - to face each other in a final battle between good and evil.",
      "origin_country": [
        "US"
      ],
      "popularity": 18.406
    },
    {
      "original_name": "The Strain",
      "id": 47640,
      "name": "The Strain",
      "vote_count": 933,
      "vote_average": 7.3,
      "first_air_date": "2014-07-13",
      "poster_path": "/2BWErT9QcADpf2G4BZ769eSnFTP.jpg",
      "genre_ids": [
        18,
        9648,
        878
      ],
      "original_language": "en",
      "backdrop_path": "/4YnAZ1KO9goeS4c06VO7HXiO3oO.jpg",
      "overview": "A high concept thriller that tells the story of Dr. Ephraim Goodweather, the head of the Center for Disease Control Canary Team in New York City. He and his team are called upon to investigate a mysterious viral outbreak with hallmarks of an ancient and evil strain of vampirism. As the strain spreads, Eph, his team, and an assembly of everyday New Yorkers, wage war for the fate of humanity itself.",
      "origin_country": [
        "US"
      ],
      "popularity": 58.533
    },
    {
      "original_name": "アナザー",
      "id": 42589,
      "name": "Another",
      "vote_count": 387,
      "vote_average": 8.4,
      "first_air_date": "2012-01-10",
      "poster_path": "/cwaEn7Gg11avVtn1BZ5qaFV1aEd.jpg",
      "genre_ids": [
        10759,
        16,
        18,
        9648
      ],
      "original_language": "ja",
      "backdrop_path": "/a8z6VxePR6Xp3NfHkNsZj87LxVr.jpg",
      "overview": "When Kouichi Sasakibara transfers to his new school, he can sense something frightening in the atmosphere of his new class, a secret none of them will talk about. At the center is the beautiful girl Mei Misaki. Kouichi is immediately drawn to her mysterious aura, but then he begins to realize that no one else in the class is aware of her presence.",
      "origin_country": [
        "JP"
      ],
      "popularity": 39.69
    },
    {
      "original_name": "DC's Legends of Tomorrow",
      "id": 62643,
      "name": "DC's Legends of Tomorrow",
      "vote_count": 1368,
      "vote_average": 7.1,
      "first_air_date": "2016-01-21",
      "poster_path": "/yJ3xE11IDIe29LJsSbhzwt5Oxtd.jpg",
      "genre_ids": [
        10759,
        18,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/be8fOACxsVyaX6lZLlQOWNqF0g2.jpg",
      "overview": "When heroes alone are not enough ... the world needs legends. Having seen the future, one he will desperately try to prevent from happening, time-traveling rogue Rip Hunter is tasked with assembling a disparate group of both heroes and villains to confront an unstoppable threat — one in which not only is the planet at stake, but all of time itself. Can this ragtag team defeat an immortal threat unlike anything they have ever known?",
      "origin_country": [
        "US"
      ],
      "popularity": 108.32
    },
    {
      "original_name": "La valla",
      "id": 93522,
      "name": "The Barrier",
      "vote_count": 6,
      "vote_average": 4.3,
      "first_air_date": "2020-01-19",
      "poster_path": "/qvlAuCWqBkXH9tO9kO6CTupA9XT.jpg",
      "genre_ids": [
        18,
        10765,
        10768,
        9648
      ],
      "original_language": "es",
      "backdrop_path": "/mJfXmAD7ABHla1fuzL29rZqoxrN.jpg",
      "overview": "One family’s fight for survival in a future dystopian Madrid illustrates the disparity between two worlds separated by a fence — and so much more.",
      "origin_country": [
        "ES"
      ],
      "popularity": 67.197
    },
    {
      "original_name": "I Am Not Okay with This",
      "id": 90260,
      "name": "I Am Not Okay with This",
      "vote_count": 929,
      "vote_average": 8.3,
      "first_air_date": "2020-02-26",
      "poster_path": "/kf3yX0ILNlLJ42X3lX2iYJ3QRp6.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/9HWumm63coTQGsveyZttmoGJ0NZ.jpg",
      "overview": "A teen navigates the complexities of high school, family and her sexuality while dealing with new superpowers. Based on Charles Forsman's graphic novel.",
      "origin_country": [
        "US"
      ],
      "popularity": 51.653
    },
    {
      "original_name": "Jericho",
      "id": 365,
      "name": "Jericho",
      "vote_count": 227,
      "vote_average": 7.4,
      "first_air_date": "2006-09-20",
      "poster_path": "/a57H9UsS388Av2LSLKO9inNmY7j.jpg",
      "genre_ids": [
        18,
        9648,
        10759
      ],
      "original_language": "en",
      "backdrop_path": "/zZmc68xm439AKsZZJ9y0qgkq8Hl.jpg",
      "overview": "Jericho is an American action/drama series that centers on the residents of the fictional town of Jericho, Kansas, in the aftermath of nuclear attacks on 23 major cities in the contiguous United States.",
      "origin_country": [
        "US"
      ],
      "popularity": 33.769
    },
    {
      "backdrop_path": "/4ZdEMS47PnUK98uYCjh1t5UOJp8.jpg",
      "first_air_date": "2015-03-17",
      "genre_ids": [
        27,
        18,
        80,
        10765
      ],
      "id": 60866,
      "name": "iZombie",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "iZombie",
      "overview": "A medical student who becomes a zombie joins a Coroner's Office in order to gain access to the brains she must reluctantly eat so that she can maintain her humanity. But every brain she eats, she also inherits their memories and must now solve their deaths with help from the Medical examiner and a police detective.",
      "poster_path": "/q4nqNwAhzVR7JuYctrWJvUWz3xR.jpg",
      "vote_average": 7.7,
      "vote_count": 1462,
      "popularity": 84.056
    },
    {
      "backdrop_path": "/xNNplu208Twucpf8MgnqHysYPg.jpg",
      "first_air_date": "2012-10-04",
      "genre_ids": [
        16,
        10759,
        18,
        10765
      ],
      "id": 45125,
      "name": "Btooom!",
      "origin_country": [
        "JP"
      ],
      "original_language": "ja",
      "original_name": "BTOOOM!",
      "overview": "Sakamoto Ryouta is an unemployed 22-year-old who lives with his mother. In the real world, there may be nothing really special about him, but online, he's one of the world's top players of the combat game Btooom!. One day, he awakes on what appears to be a tropical island, though he has no memory of how or why he has come to be there. While wandering around, Ryouta sees someone and calls out for help. The stranger responds by throwing a bomb at him! Now Ryouta realizes both that his life is in danger and that he has somehow been trapped in a real-life version of his favourite game! Will Ryouta be able to survive long enough to figure out how and why he ended up here?",
      "poster_path": "/9WGXIXqq1Y12KyfxEVmtaDe9b1H.jpg",
      "vote_average": 7.5,
      "vote_count": 83,
      "popularity": 22.74
    },
    {
      "backdrop_path": "/qCReQONjNVaW4QFN2V5vCOzPvex.jpg",
      "first_air_date": "2020-10-16",
      "genre_ids": [
        10759,
        10765,
        18
      ],
      "id": 88987,
      "name": "Helstrom",
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "Helstrom",
      "overview": "Daimon and Ana Helstrom are the son and daughter of a mysterious and powerful serial killer. The siblings have a complicated dynamic as they track down the terrorizing worst of humanity — each with their attitude and skills.",
      "poster_path": "/8fPIcaRIZgsoBcgA5yAm3GNz61M.jpg",
      "vote_average": 7,
      "vote_count": 51,
      "popularity": 23.848
    }
  ],
  "total_pages": 48,
  "total_results": 958
}"""

        const val favouriteTVShows = """{
  "page": 1,
  "results": [
    {
      "original_name": "The Blacklist",
      "id": 46952,
      "name": "The Blacklist",
      "vote_count": 1702,
      "vote_average": 7.3,
      "first_air_date": "2013-09-23",
      "poster_path": "/htJzeRcYI2ewMm4PTrg98UMXShe.jpg",
      "genre_ids": [
        18,
        80,
        9648
      ],
      "original_language": "en",
      "backdrop_path": "/zXpSJLcczUt6EfbdULZanguzy87.jpg",
      "overview": "Raymond \"Red\" Reddington, one of the FBI's most wanted fugitives, surrenders in person at FBI Headquarters in Washington, D.C. He claims that he and the FBI have the same interests: bringing down dangerous criminals and terrorists. In the last two decades, he's made a list of criminals and terrorists that matter the most but the FBI cannot find because it does not know they exist. Reddington calls this \"The Blacklist\". Reddington will co-operate, but insists that he will speak only to Elizabeth Keen, a rookie FBI profiler.",
      "origin_country": [
        "US"
      ],
      "popularity": 348.602
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "id": 94305,
      "name": "The Walking Dead: World Beyond",
      "vote_count": 421,
      "vote_average": 7.8,
      "first_air_date": "2020-10-04",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "original_language": "en",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world receive a message that inspires them to leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "origin_country": [
        "US"
      ],
      "popularity": 367.83
    },
    {
      "original_name": "The Simpsons",
      "id": 456,
      "name": "The Simpsons",
      "vote_count": 5288,
      "vote_average": 7.7,
      "first_air_date": "1989-12-16",
      "poster_path": "/2IWouZK4gkgHhJa3oyYuSWfSqbG.jpg",
      "genre_ids": [
        16,
        35,
        10751,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/hpU2cHC9tk90hswCFEpf5AtbqoL.jpg",
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "origin_country": [
        "US"
      ],
      "popularity": 451.17
    },
    {
      "original_name": "The Queen's Gambit",
      "id": 87739,
      "name": "The Queen's Gambit",
      "vote_count": 399,
      "vote_average": 8.9,
      "first_air_date": "2020-10-23",
      "poster_path": "/zU0htwkhNvBQdVSIKB9s6hgVeFK.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/5N5dSOrysuquExvn8Gpp5jMEf6u.jpg",
      "overview": "In a Kentucky orphanage in the 1950s, a young girl discovers an astonishing talent for chess while struggling with addiction.",
      "origin_country": [
        "US"
      ],
      "popularity": 431.706
    },
    {
      "original_name": "The Umbrella Academy",
      "id": 75006,
      "name": "The Umbrella Academy",
      "vote_count": 3954,
      "vote_average": 8.7,
      "first_air_date": "2019-02-15",
      "poster_path": "/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg",
      "genre_ids": [
        10759,
        10765,
        35,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/mE3zzMkpP8yqlkzdjPsQmJHceoe.jpg",
      "overview": "A dysfunctional family of superheroes comes together to solve the mystery of their father's death, the threat of the apocalypse and more.",
      "origin_country": [
        "US"
      ],
      "popularity": 441.766
    },
    {
      "original_name": "The Boys",
      "id": 76479,
      "name": "The Boys",
      "vote_count": 3551,
      "vote_average": 8.4,
      "first_air_date": "2019-07-25",
      "poster_path": "/mY7SeH4HFFxW1hiI6cWuwCRKptN.jpg",
      "genre_ids": [
        10765,
        10759
      ],
      "original_language": "en",
      "backdrop_path": "/mGVrXeIjyecj6TKmwPVpHlscEmw.jpg",
      "overview": "A group of vigilantes known informally as “The Boys” set out to take down corrupt superheroes with no more than blue-collar grit and a willingness to fight dirty.",
      "origin_country": [
        "US"
      ],
      "popularity": 479.427
    },
    {
      "original_name": "Lucifer",
      "id": 63174,
      "name": "Lucifer",
      "vote_count": 6355,
      "vote_average": 8.5,
      "first_air_date": "2016-01-25",
      "poster_path": "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
      "genre_ids": [
        80,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg",
      "overview": "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
      "origin_country": [
        "US"
      ],
      "popularity": 604.45
    },
    {
      "original_name": "His Dark Materials",
      "id": 68507,
      "name": "His Dark Materials",
      "vote_count": 551,
      "vote_average": 8.1,
      "first_air_date": "2019-11-03",
      "poster_path": "/g6tIKGc3f1H5QMz1dcgCwADKpZ7.jpg",
      "genre_ids": [
        18,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/9yKCJTOh9m3Lol2RY3kw99QPH6x.jpg",
      "overview": "Lyra is an orphan who lives in a parallel universe in which science, theology and magic are entwined. Lyra's search for a kidnapped friend uncovers a sinister plot involving stolen children, and turns into a quest to understand a mysterious phenomenon called Dust. She is later joined on her journey by Will, a boy who possesses a knife that can cut windows between worlds. As Lyra learns the truth about her parents and her prophesied destiny, the two young people are caught up in a war against celestial powers that ranges across many worlds.",
      "origin_country": [
        "GB"
      ],
      "popularity": 507.044
    },
    {
      "original_name": "Fear the Walking Dead",
      "id": 62286,
      "name": "Fear the Walking Dead",
      "vote_count": 2622,
      "vote_average": 7.4,
      "first_air_date": "2015-08-23",
      "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg",
      "genre_ids": [
        10759,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "origin_country": [
        "US"
      ],
      "popularity": 627.312
    },
    {
      "original_name": "Grey's Anatomy",
      "id": 1416,
      "name": "Grey's Anatomy",
      "vote_count": 4194,
      "vote_average": 8.1,
      "first_air_date": "2005-03-27",
      "poster_path": "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/zWX629wryQwpaIwsquP4BRUD6HT.jpg",
      "overview": "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
      "origin_country": [
        "US"
      ],
      "popularity": 1039.483
    },
    {
      "original_name": "The Good Doctor",
      "id": 71712,
      "name": "The Good Doctor",
      "vote_count": 5619,
      "vote_average": 8.6,
      "first_air_date": "2017-09-25",
      "poster_path": "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
      "overview": "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
      "origin_country": [
        "US"
      ],
      "popularity": 1127.576
    },
    {
      "original_name": "The Mandalorian",
      "id": 82856,
      "name": "The Mandalorian",
      "vote_count": 2455,
      "vote_average": 8.5,
      "first_air_date": "2019-11-12",
      "poster_path": "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
      "genre_ids": [
        10765,
        10759,
        37
      ],
      "original_language": "en",
      "backdrop_path": "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
      "overview": "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
      "origin_country": [
        "US"
      ],
      "popularity": 1717.271
    }
  ],
  "total_pages": 1,
  "total_results": 12
}"""

        const val watchListTV = """{
  "page": 1,
  "results": [
    {
      "original_name": "The Blacklist",
      "id": 46952,
      "name": "The Blacklist",
      "vote_count": 1702,
      "vote_average": 7.3,
      "first_air_date": "2013-09-23",
      "poster_path": "/htJzeRcYI2ewMm4PTrg98UMXShe.jpg",
      "genre_ids": [
        18,
        80,
        9648
      ],
      "original_language": "en",
      "backdrop_path": "/zXpSJLcczUt6EfbdULZanguzy87.jpg",
      "overview": "Raymond \"Red\" Reddington, one of the FBI's most wanted fugitives, surrenders in person at FBI Headquarters in Washington, D.C. He claims that he and the FBI have the same interests: bringing down dangerous criminals and terrorists. In the last two decades, he's made a list of criminals and terrorists that matter the most but the FBI cannot find because it does not know they exist. Reddington calls this \"The Blacklist\". Reddington will co-operate, but insists that he will speak only to Elizabeth Keen, a rookie FBI profiler.",
      "origin_country": [
        "US"
      ],
      "popularity": 348.602
    },
    {
      "original_name": "The Walking Dead: World Beyond",
      "id": 94305,
      "name": "The Walking Dead: World Beyond",
      "vote_count": 421,
      "vote_average": 7.8,
      "first_air_date": "2020-10-04",
      "poster_path": "/z31GxpVgDsFAF4paZR8PRsiP16D.jpg",
      "genre_ids": [
        18,
        10765,
        9648
      ],
      "original_language": "en",
      "backdrop_path": "/pLVrN9B750ehwTFdQ6n3HRUERLd.jpg",
      "overview": "A heroic group of teens sheltered from the dangers of the post-apocalyptic world receive a message that inspires them to leave the safety of the only home they have ever known and embark on a cross-country journey to find the one man who can possibly save the world.",
      "origin_country": [
        "US"
      ],
      "popularity": 367.83
    },
    {
      "original_name": "The Simpsons",
      "id": 456,
      "name": "The Simpsons",
      "vote_count": 5288,
      "vote_average": 7.7,
      "first_air_date": "1989-12-16",
      "poster_path": "/2IWouZK4gkgHhJa3oyYuSWfSqbG.jpg",
      "genre_ids": [
        16,
        35,
        10751,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/hpU2cHC9tk90hswCFEpf5AtbqoL.jpg",
      "overview": "Set in Springfield, the average American town, the show focuses on the antics and everyday adventures of the Simpson family; Homer, Marge, Bart, Lisa and Maggie, as well as a virtual cast of thousands. Since the beginning, the series has been a pop culture icon, attracting hundreds of celebrities to guest star. The show has also made name for itself in its fearless satirical take on politics, media and American life in general.",
      "origin_country": [
        "US"
      ],
      "popularity": 451.17
    },
    {
      "original_name": "The Queen's Gambit",
      "id": 87739,
      "name": "The Queen's Gambit",
      "vote_count": 399,
      "vote_average": 8.9,
      "first_air_date": "2020-10-23",
      "poster_path": "/zU0htwkhNvBQdVSIKB9s6hgVeFK.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/5N5dSOrysuquExvn8Gpp5jMEf6u.jpg",
      "overview": "In a Kentucky orphanage in the 1950s, a young girl discovers an astonishing talent for chess while struggling with addiction.",
      "origin_country": [
        "US"
      ],
      "popularity": 431.706
    },
    {
      "original_name": "The Umbrella Academy",
      "id": 75006,
      "name": "The Umbrella Academy",
      "vote_count": 3954,
      "vote_average": 8.7,
      "first_air_date": "2019-02-15",
      "poster_path": "/scZlQQYnDVlnpxFTxaIv2g0BWnL.jpg",
      "genre_ids": [
        10759,
        10765,
        35,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/mE3zzMkpP8yqlkzdjPsQmJHceoe.jpg",
      "overview": "A dysfunctional family of superheroes comes together to solve the mystery of their father's death, the threat of the apocalypse and more.",
      "origin_country": [
        "US"
      ],
      "popularity": 441.766
    },
    {
      "original_name": "The Boys",
      "id": 76479,
      "name": "The Boys",
      "vote_count": 3551,
      "vote_average": 8.4,
      "first_air_date": "2019-07-25",
      "poster_path": "/mY7SeH4HFFxW1hiI6cWuwCRKptN.jpg",
      "genre_ids": [
        10765,
        10759
      ],
      "original_language": "en",
      "backdrop_path": "/mGVrXeIjyecj6TKmwPVpHlscEmw.jpg",
      "overview": "A group of vigilantes known informally as “The Boys” set out to take down corrupt superheroes with no more than blue-collar grit and a willingness to fight dirty.",
      "origin_country": [
        "US"
      ],
      "popularity": 479.427
    },
    {
      "original_name": "Lucifer",
      "id": 63174,
      "name": "Lucifer",
      "vote_count": 6355,
      "vote_average": 8.5,
      "first_air_date": "2016-01-25",
      "poster_path": "/4EYPN5mVIhKLfxGruy7Dy41dTVn.jpg",
      "genre_ids": [
        80,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/ta5oblpMlEcIPIS2YGcq9XEkWK2.jpg",
      "overview": "Bored and unhappy as the Lord of Hell, Lucifer Morningstar abandoned his throne and retired to Los Angeles, where he has teamed up with LAPD detective Chloe Decker to take down criminals. But the longer he's away from the underworld, the greater the threat that the worst of humanity could escape.",
      "origin_country": [
        "US"
      ],
      "popularity": 604.45
    },
    {
      "original_name": "His Dark Materials",
      "id": 68507,
      "name": "His Dark Materials",
      "vote_count": 551,
      "vote_average": 8.1,
      "first_air_date": "2019-11-03",
      "poster_path": "/g6tIKGc3f1H5QMz1dcgCwADKpZ7.jpg",
      "genre_ids": [
        18,
        10765
      ],
      "original_language": "en",
      "backdrop_path": "/9yKCJTOh9m3Lol2RY3kw99QPH6x.jpg",
      "overview": "Lyra is an orphan who lives in a parallel universe in which science, theology and magic are entwined. Lyra's search for a kidnapped friend uncovers a sinister plot involving stolen children, and turns into a quest to understand a mysterious phenomenon called Dust. She is later joined on her journey by Will, a boy who possesses a knife that can cut windows between worlds. As Lyra learns the truth about her parents and her prophesied destiny, the two young people are caught up in a war against celestial powers that ranges across many worlds.",
      "origin_country": [
        "GB"
      ],
      "popularity": 507.044
    },
    {
      "original_name": "Fear the Walking Dead",
      "id": 62286,
      "name": "Fear the Walking Dead",
      "vote_count": 2622,
      "vote_average": 7.4,
      "first_air_date": "2015-08-23",
      "poster_path": "/wGFUewXPeMErCe2xnCmmLEiHOGh.jpg",
      "genre_ids": [
        10759,
        18
      ],
      "original_language": "en",
      "backdrop_path": "/58PON1OrnBiX6CqEHgeWKVwrCn6.jpg",
      "overview": "What did the world look like as it was transforming into the horrifying apocalypse depicted in \"The Walking Dead\"? This spin-off set in Los Angeles, following new characters as they face the beginning of the end of the world, will answer that question.",
      "origin_country": [
        "US"
      ],
      "popularity": 627.312
    },
    {
      "original_name": "Grey's Anatomy",
      "id": 1416,
      "name": "Grey's Anatomy",
      "vote_count": 4194,
      "vote_average": 8.1,
      "first_air_date": "2005-03-27",
      "poster_path": "/clnyhPqj1SNgpAdeSS6a6fwE6Bo.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/zWX629wryQwpaIwsquP4BRUD6HT.jpg",
      "overview": "Follows the personal and professional lives of a group of doctors at Seattle’s Grey Sloan Memorial Hospital.",
      "origin_country": [
        "US"
      ],
      "popularity": 1039.483
    },
    {
      "original_name": "The Good Doctor",
      "id": 71712,
      "name": "The Good Doctor",
      "vote_count": 5619,
      "vote_average": 8.6,
      "first_air_date": "2017-09-25",
      "poster_path": "/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg",
      "genre_ids": [
        18
      ],
      "original_language": "en",
      "backdrop_path": "/iDbIEpCM9nhoayUDTwqFL1iVwzb.jpg",
      "overview": "A young surgeon with Savant syndrome is recruited into the surgical unit of a prestigious hospital. The question will arise: can a person who doesn't have the ability to relate to people actually save their lives?",
      "origin_country": [
        "US"
      ],
      "popularity": 1127.576
    },
    {
      "original_name": "The Mandalorian",
      "id": 82856,
      "name": "The Mandalorian",
      "vote_count": 2455,
      "vote_average": 8.5,
      "first_air_date": "2019-11-12",
      "poster_path": "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
      "genre_ids": [
        10765,
        10759,
        37
      ],
      "original_language": "en",
      "backdrop_path": "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg",
      "overview": "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
      "origin_country": [
        "US"
      ],
      "popularity": 1717.271
    }
  ],
  "total_pages": 1,
  "total_results": 12
}"""

        const val sampleSessionId = "f5c1c3b446df720962376168620907f8052d4e95"

        const val accountDetailResponse = """{
  "avatar": {
    "gravatar": {
      "hash": "c4bc3a0eae31820916cfbabf17cb29a9"
    },
    "tmdb": {
      "avatar_path": "/3bk7ZEoQ1XU5dJSe1qp6oJxEJMd.jpg"
    }
  },
  "id": 8781441,
  "iso_639_1": "en",
  "iso_3166_1": "US",
  "name": "",
  "include_adult": false,
  "username": "rachamanabdau"
}"""
    }
}