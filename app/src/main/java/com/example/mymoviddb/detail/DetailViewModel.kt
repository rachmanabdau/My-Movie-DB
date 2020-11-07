package com.example.mymoviddb.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.*
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(private val detailaAccess: IDetailAccess) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetail?>>(Result.Loading)
    val movieDetail: LiveData<Result<MovieDetail?>> = _movieDetail

    private val _recommendationMovies = MutableLiveData<Result<MovieModel?>>()
    val recommendationMovies: LiveData<Result<MovieModel?>> = _recommendationMovies

    private val _similarMovies = MutableLiveData<Result<MovieModel?>>()
    val similarMovies: LiveData<Result<MovieModel?>> = _similarMovies

    private val _recommendationTVShows = MutableLiveData<Result<TVShowModel?>>()
    val recommendationTVShows: LiveData<Result<TVShowModel?>> = _recommendationTVShows

    private val _similarTVShows = MutableLiveData<Result<TVShowModel?>>()
    val similarTVShows: LiveData<Result<TVShowModel?>> = _similarTVShows

    private val _tvDetail = MutableLiveData<Result<TVDetail?>>()
    val tvDetail: LiveData<Result<TVDetail?>> = _tvDetail

    private val _mediaAccountState = MutableLiveData<Result<MediaState?>>()
    val mediaState: LiveData<Result<MediaState?>> = _mediaAccountState

    private val _favouriteResult = MutableLiveData<Result<Error401Model?>>()
    val favouriteResult: LiveData<Result<Error401Model?>> = _favouriteResult

    fun getMovieDetail(movieId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _movieDetail.value = detailaAccess.getDetailMovie(movieId, apiKey)
        }
    }

    fun getTVDetail(tvId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _tvDetail.value = detailaAccess.getDetailTV(tvId, apiKey)
        }
    }

    fun getRecommendationMovies(movieId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _recommendationMovies.value = detailaAccess.getRecommendationMovies(movieId, apiKey)
        }
    }

    fun getSimilarMovies(movieId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _similarMovies.value = detailaAccess.getSimilarMovies(movieId, apiKey)
        }
    }

    fun getRecommendationTVShows(tvId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _recommendationTVShows.value = detailaAccess.getRecommendationTVShows(tvId, apiKey)
        }
    }

    fun getSimilarTVShows(tvId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _similarTVShows.value = detailaAccess.getSimilarTVShows(tvId, apiKey)
        }
    }

    fun getMovieAccountState(
        movieId: Long,
        sessionId: String,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            _mediaAccountState.value = detailaAccess.getMovieAuthState(movieId, sessionId, apiKey)
        }
    }

    fun getTVAccountState(tvId: Long, sessionId: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _mediaAccountState.value = detailaAccess.getTVAuthState(tvId, sessionId, apiKey)
        }
    }

    fun markAsFavorite(
        accountId: Int,
        sessionId: String,
        mediaType: MarkAsFavorite,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            _favouriteResult.value =
                detailaAccess.markAsFavorite(accountId, sessionId, mediaType, apiKey)
        }
    }
}
