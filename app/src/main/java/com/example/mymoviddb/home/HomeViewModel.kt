package com.example.mymoviddb.home

import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.model.succeeded
import com.example.mymoviddb.utils.Event
import kotlinx.coroutines.launch

class HomeViewModel(
    private val remoteServer: HomeAccess
) : ViewModel() {

    private val _popularMovieList = MutableLiveData<Result<MovieModel?>>()
    val popularMovieList: LiveData<Result<MovieModel?>> = _popularMovieList

    private val _nowPlayingMovieList = MutableLiveData<Result<MovieModel?>>()
    val nowPlayingMovieList: LiveData<Result<MovieModel?>> = _nowPlayingMovieList

    private val _popularTVList = MutableLiveData<Result<TVShowModel?>>()
    val popularTVList: LiveData<Result<TVShowModel?>> = _popularTVList

    private val _onAirTVList = MutableLiveData<Result<TVShowModel?>>()
    val onAirTVList: LiveData<Result<TVShowModel?>> = _onAirTVList

    private val _showPopularMovieError = MutableLiveData<Boolean>()
    val showPopularMovieError: LiveData<Boolean>
        get() = _showPopularMovieError

    private val _showNowPlayingMovieError = MutableLiveData<Boolean>()
    val showNowPlayingMovieError: LiveData<Boolean>
        get() = _showNowPlayingMovieError

    private val _showPopularTvError = MutableLiveData<Boolean>()
    val showPopularTvError: LiveData<Boolean>
        get() = _showPopularTvError

    private val _showOnAirTvError = MutableLiveData<Boolean>()
    val showOnAirTvError: LiveData<Boolean>
        get() = _showOnAirTvError

    private val _initialLoading = MutableLiveData<Boolean>()
    val initialLoading: LiveData<Boolean>
        get() = _initialLoading

    private val _snackbarMessage = MutableLiveData<Event<String>>()
    val snackbarMessage: LiveData<Event<String>>
        get() = _snackbarMessage

    init {
        populateData()
        _showPopularMovieError.value = false
        _showNowPlayingMovieError.value = false
        _showPopularTvError.value = false
        _showOnAirTvError.value = false
        _initialLoading.value = true
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
            _snackbarMessage.value = Event(getMessage)
        }
    }

    class Factory(
        private val remoteSource: HomeAccess
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(remoteSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}