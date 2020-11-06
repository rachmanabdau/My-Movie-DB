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
            _similarMovies.value = detailaAccess.getSimialrMovies(movieId, apiKey)
        }
    }

    fun getRecommendationTVShows(movieId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _recommendationTVShows.value = detailaAccess.getRecommendationTVShows(movieId, apiKey)
        }
    }

    fun getSimilarTVShows(movieId: Long, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _similarTVShows.value = detailaAccess.getSimialrTVShows(movieId, apiKey)
        }
    }
}
