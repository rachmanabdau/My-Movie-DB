package com.example.mymoviddb.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.PreviewMovie
import com.example.mymoviddb.core.model.PreviewTvShow
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.succeeded
import com.example.mymoviddb.core.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteServer: IHomeAccess

) : ViewModel() {

    private val _popularMovieList = MutableLiveData<Result<PreviewMovie?>>()
    val popularMovieList: LiveData<Result<PreviewMovie?>> = _popularMovieList

    private val _nowPlayingMovieList = MutableLiveData<Result<PreviewMovie?>>()
    val nowPlayingMovieList: LiveData<Result<PreviewMovie?>> = _nowPlayingMovieList

    private val _popularTVList = MutableLiveData<Result<PreviewTvShow?>>()
    val popularTVList: LiveData<Result<PreviewTvShow?>> = _popularTVList

    private val _onAirTVList = MutableLiveData<Result<PreviewTvShow?>>()
    val onAirTVList: LiveData<Result<PreviewTvShow?>> = _onAirTVList

    private val _showPopularMovieError = MutableLiveData(false)
    val showPopularMovieError: LiveData<Boolean>
        get() = _showPopularMovieError

    private val _showNowPlayingMovieError = MutableLiveData(false)
    val showNowPlayingMovieError: LiveData<Boolean>
        get() = _showNowPlayingMovieError

    private val _showPopularTvError = MutableLiveData(false)
    val showPopularTvError: LiveData<Boolean>
        get() = _showPopularTvError

    private val _showOnAirTvError = MutableLiveData(false)
    val showOnAirTvError: LiveData<Boolean>
        get() = _showOnAirTvError

    private val _initialLoading = MutableLiveData(true)
    val initialLoading: LiveData<Boolean>
        get() = _initialLoading

    private val _snackbarMessage = MutableLiveData<Event<String>>()
    val snackbarMessage: LiveData<Event<String>>
        get() = _snackbarMessage

    init {
        populateData()
    }

    private fun populateData() {
        getPopularMovieList()
        getNowPlayingMovieList()
        getPopularTVList()
        getonAirTVList()
    }

    fun getPopularMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            val result = remoteServer.getPopularMovieList(page, apiKey)
            _popularMovieList.value = result
            _showPopularMovieError.value = !result.succeeded
            setSnackbarMessage(result)
        }
    }

    fun getNowPlayingMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            val result = remoteServer.getNowPlayingMovieList(page, apiKey)
            _nowPlayingMovieList.value = result
            _showNowPlayingMovieError.value = !result.succeeded
            setSnackbarMessage(result)
        }
    }

    fun getPopularTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            val result = remoteServer.getPopularTvShowList(page, apiKey)
            _popularTVList.value = result
            _showPopularTvError.value = !result.succeeded
            setSnackbarMessage(result)
        }
    }

    fun getonAirTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            val result = remoteServer.getOnAirTvShowList(page, apiKey)
            _onAirTVList.value = result
            _showOnAirTvError.value = !result.succeeded
            setSnackbarMessage(result)
            _initialLoading.value = false
        }
    }

    private fun setSnackbarMessage(message: Result<*>) {
        if (message is Result.Error) {
            val getMessage = message.exception.localizedMessage
            _snackbarMessage.value = Event(getMessage ?: "Unknown error has occured.")
        }
    }
}