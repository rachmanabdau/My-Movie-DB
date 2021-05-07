package com.example.mymoviddb.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.model.succeeded
import com.example.mymoviddb.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteServer: IHomeAccess

) : ViewModel() {

    private val _popularMovieList = MutableLiveData<Result<MovieModel?>>()
    val popularMovieList: LiveData<Result<MovieModel?>> = _popularMovieList

    private val _nowPlayingMovieList = MutableLiveData<Result<MovieModel?>>()
    val nowPlayingMovieList: LiveData<Result<MovieModel?>> = _nowPlayingMovieList

    private val _popularTVList = MutableLiveData<Result<TVShowModel?>>()
    val popularTVList: LiveData<Result<TVShowModel?>> = _popularTVList

    private val _onAirTVList = MutableLiveData<Result<TVShowModel?>>()
    val onAirTVList: LiveData<Result<TVShowModel?>> = _onAirTVList

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
        viewModelScope.launch {
            getPopularMovieList()
            getNowPlayingMovieList()
            getPopularTVList()
            getonAirTVList()
        }
    }

    suspend fun getPopularMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _popularMovieList.value = remoteServer.getPopularMovieList(page, apiKey)
        val result = _popularMovieList.value
        _showPopularMovieError.value = !(result?.succeeded)!!
        setSnackbarMessage(result)
    }

    suspend fun getNowPlayingMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _nowPlayingMovieList.value = remoteServer.getNowPlayingMovieList(page, apiKey)
        val result = _nowPlayingMovieList.value
        _showNowPlayingMovieError.value = !(result?.succeeded)!!
        setSnackbarMessage(result)
    }

    suspend fun getPopularTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _popularTVList.value = remoteServer.getPopularTvShowList(page, apiKey)
        val result = _popularTVList.value
        _showPopularTvError.value = !(result?.succeeded)!!
        setSnackbarMessage(result)
    }

    suspend fun getonAirTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _onAirTVList.value = remoteServer.getOnAirTvShowList(page, apiKey)
        val result = _onAirTVList.value
        _showOnAirTvError.value = !(result?.succeeded)!!
        setSnackbarMessage(result)
        _initialLoading.value = false
    }

    private fun setSnackbarMessage(message: Result<*>) {
        if (message is Result.Error) {
            val getMessage = message.exception.localizedMessage
            _snackbarMessage.value = Event(getMessage ?: "Unknown error has occured.")
        }
    }
}