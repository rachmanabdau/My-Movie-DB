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

data class DataDetailHelper(
    val showTitle: String,
    val overview: String,
    val rate: Int,
    val genre: String,
    val posterPath: String?,
    val date: String
)

@HiltViewModel
class DetailViewModel @Inject constructor(private val detailaAccess: IDetailAccess) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetail?>>(Result.Loading)
    val movieDetail: LiveData<Result<MovieDetail?>> = _movieDetail

    private val _recommendationMovies = MutableLiveData<Result<PreviewMovie?>>()
    val recommendationMovies: LiveData<Result<PreviewMovie?>> = _recommendationMovies

    private val _similarMovies = MutableLiveData<Result<PreviewMovie?>>()
    val similarMovies: LiveData<Result<PreviewMovie?>> = _similarMovies

    private val _recommendationTVShows = MutableLiveData<Result<PreviewTvShow?>>()
    val recommendationTVShows: LiveData<Result<PreviewTvShow?>> = _recommendationTVShows

    private val _similarTVShows = MutableLiveData<Result<PreviewTvShow?>>()
    val similarTVShows: LiveData<Result<PreviewTvShow?>> = _similarTVShows

    private val _tvDetail = MutableLiveData<Result<TVDetail?>>()
    val tvDetail: LiveData<Result<TVDetail?>> = _tvDetail

    private val _showDetail = MutableLiveData<DataDetailHelper>()
    val showDetail: LiveData<DataDetailHelper> = _showDetail

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
        showId: Long,
        showType: Int,
        sessionId: String,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        when (showType) {
            DetailActivity.DETAIL_MOVIE -> getMovieDetail(showId, sessionId, apiKey)
            DetailActivity.DETAIL_TV -> getTVDetail(showId, sessionId, apiKey)
        }
    }

    fun getMovieDetail(movieId: Long, sessionId: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _movieDetail.value = detailaAccess.getDetailMovie(movieId, apiKey).also { result ->
                if (result is Result.Success) {
                    result.data?.apply {
                        val rate = (voteAverage * 10).toInt()
                        val genre = genres.joinToString { it.name }
                        _showDetail.value =
                            DataDetailHelper(title, overview, rate, genre, posterPath, releaseDate)
                        getMovieAccountState(movieId, sessionId, apiKey)
                    }
                }
            }
        }
    }

    fun getTVDetail(tvId: Long, sessionId: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _tvDetail.value = detailaAccess.getDetailTV(tvId, apiKey).also { result ->
                if (result is Result.Success) {
                    result.data?.apply {
                        val rate = (voteAverage * 10).toInt()
                        val genre = genres.joinToString { it.name }
                        _showDetail.value =
                            DataDetailHelper(title, overview, rate, genre, posterPath, firstAirDate)
                        getTVAccountState(tvId, sessionId, apiKey)
                    }
                }
            }
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
        mediaId: Long,
        mediaType: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            val media = if (mediaType == DetailActivity.DETAIL_MOVIE) "movie" else "tv"
            val sendFavourite = MarkMediaAs(mediaId, media, _isFavourited.value?.not(), null)
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
        mediaId: Long,
        mediaType: Int,
        apiKey: String = BuildConfig.V3_AUTH
    ) {
        viewModelScope.launch {
            val media = if (mediaType == DetailActivity.DETAIL_MOVIE) "movie" else "tv"
            val sendMediaWatchList =
                MarkMediaAs(mediaId, media, null, _isAddedToWatchList.value?.not())
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
