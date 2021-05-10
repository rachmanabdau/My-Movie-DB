package com.example.mymoviddb.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.R
import com.example.mymoviddb.model.*
import com.example.mymoviddb.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailaAccess: IDetailAccess) :
    ViewModel() {

    private val _showDetail = MutableLiveData<Result<ShowDetail?>>()
    val showDetail: LiveData<Result<ShowDetail?>> = _showDetail

    private val _recommendationShows = MutableLiveData<Result<ShowResponse?>>()
    val recommendationShows: LiveData<Result<ShowResponse?>> = _recommendationShows

    private val _similarShows = MutableLiveData<Result<ShowResponse?>>()
    val similarShows: LiveData<Result<ShowResponse?>> = _similarShows

    private val _isResultSuccess = MutableLiveData<Boolean>()
    val isResultSuccess: LiveData<Boolean> = _isResultSuccess

    private val _isResultLoading = MutableLiveData<Boolean>()
    val isResultLoading: LiveData<Boolean> = _isResultLoading

    private val _isResultError = MutableLiveData<Boolean>()
    val isResultError: LiveData<Boolean> = _isResultError

    private val _isFavourited = MutableLiveData(false)
    val isFavourited: LiveData<Boolean> = _isFavourited

    private val _isAddedToWatchList = MutableLiveData(false)
    val isAddedToWatchList: LiveData<Boolean> = _isAddedToWatchList

    private val _showSnackbarMessage = MutableLiveData<Event<Int>>()
    val showSnackbarMessage: LiveData<Event<Int>> = _showSnackbarMessage

    fun getShowDetail(
        showItem: ShowResult,
        sessionId: String,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            val result = if (showItem is PreviewMovie.Result) {
                getMovieAccountState(showItem.id, sessionId, apiKey)
                detailaAccess.getMovieDetail(showItem.id, apiKey)
            } else {
                val id = (showItem as PreviewTvShow.Result).id
                getTVAccountState(id, sessionId, apiKey)
                detailaAccess.getDetailTV(id, apiKey)
            }
            _showDetail.value = result
        }
    }

    fun getRecommendationMovies(showItem: ShowResult, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _recommendationShows.value = if (showItem is PreviewMovie.Result) {
                detailaAccess.getRecommendationMovies(showItem.id, apiKey)
            } else {
                val id = (showItem as PreviewTvShow.Result).id
                detailaAccess.getRecommendationTVShows(id, apiKey)
            }
        }
    }

    fun getSimilarMovies(showItem: ShowResult, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _similarShows.value = if (showItem is PreviewMovie.Result) {
                detailaAccess.getSimilarMovies(showItem.id, apiKey)
            } else {
                val id = (showItem as PreviewTvShow.Result).id
                detailaAccess.getSimilarTVShows(id, apiKey)
            }
        }
    }

    private fun getMovieAccountState(
        movieId: Long,
        sessionId: String,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            detailaAccess.getMovieAuthState(movieId, sessionId, apiKey).also { result ->
                if (result is Result.Success) {
                    result.data?.apply {
                        _isFavourited.value = favorite
                        _isAddedToWatchList.value = watchlist
                    }
                }
            }
        }
    }

    private fun getTVAccountState(
        tvId: Long,
        sessionId: String,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            detailaAccess.getTVAuthState(tvId, sessionId, apiKey).also {
                if (it is Result.Success) {
                    it.data?.apply {
                        _isFavourited.value = favorite
                        _isAddedToWatchList.value = watchlist
                    }
                }
            }
        }
    }

    fun markAsFavorite(
        accountId: Int,
        sessionId: String,
        showItem: ShowResult,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            val mediaType = if (showItem is PreviewMovie.Result) "movie" else "tv"
            val sendFavourite =
                MarkMediaAs(showItem.id, mediaType, _isFavourited.value?.not(), null)
            detailaAccess.markAsFavorite(accountId, sessionId, sendFavourite, apiKey)
                .also { result ->
                    if (result is Result.Success) {
                        result.data?.apply {
                            _isFavourited.value = result.data.statusCode != 13
                            _showSnackbarMessage.value =
                                Event(if (result.data.statusCode != 13) R.string.add_to_favourite_success else R.string.remove_from_favourite_success)
                        }
                    } else if (result is Result.Error) {
                        _isFavourited.value?.also {
                            _showSnackbarMessage.value =
                                Event(if (it) R.string.remove_from_favourite_failed else R.string.add_to_favourite_failed)
                        }
                    }
                }
        }
    }

    fun addToWatchList(
        accountId: Int,
        sessionId: String,
        showItem: ShowResult,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            val mediaType = if (showItem is PreviewMovie.Result) "movie" else "tv"
            val sendMediaWatchList =
                MarkMediaAs(showItem.id, mediaType, null, _isAddedToWatchList.value?.not())
            detailaAccess.addToWatchList(accountId, sessionId, sendMediaWatchList, apiKey)
                .also { result ->
                    if (result is Result.Success) {
                        result.data?.apply {
                            _isAddedToWatchList.value = result.data.statusCode != 13
                            _showSnackbarMessage.value =
                                Event(if (result.data.statusCode != 13) R.string.add_to_watchlist_success else R.string.remove_from_watchlist_success)
                        }
                    } else if (result is Result.Error) {
                        _isAddedToWatchList.value?.also {
                            _showSnackbarMessage.value =
                                Event(if (it) R.string.remove_from_watchlist_failed else R.string.add_to_watchlist_failed)
                        }
                    }
                }
        }
    }

    fun determineDetailResult(data: Result<*>) {
        _isResultLoading.value = data is Result.Loading
        _isResultSuccess.value = data is Result.Success
        _isResultError.value = data is Result.Error
    }
}
