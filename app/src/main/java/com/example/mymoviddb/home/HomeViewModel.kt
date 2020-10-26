package com.example.mymoviddb.home

import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
import com.example.mymoviddb.model.succeeded
import kotlinx.coroutines.launch

class HomeViewModel(
    private val renoteSource: RemoteServer
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

    init {
        populateData()
        _showPopularMovieError.value = false
        _showNowPlayingMovieError.value = false
        _showPopularTvError.value = false
        _showOnAirTvError.value = false
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
        _popularMovieList.value = renoteSource.getPopularMovieList(page, apiKey)
        _showPopularMovieError.value = !(_popularMovieList.value?.succeeded)!!
    }

    suspend fun getNowPlayingMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _nowPlayingMovieList.value = renoteSource.getNowPlayingMovieList(page, apiKey)
        _showNowPlayingMovieError.value = !(_nowPlayingMovieList.value?.succeeded)!!
    }

    suspend fun getPopularTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _popularTVList.value = renoteSource.getPopularTvShowList(page, apiKey)
        _showPopularTvError.value = !(_popularTVList.value?.succeeded)!!
    }

    suspend fun getonAirTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        _onAirTVList.value = renoteSource.getOnAirTvShowList(page, apiKey)
        _showOnAirTvError.value = !(_onAirTVList.value?.succeeded)!!
    }

    class Factory(
        private val remoteSource: RemoteServer
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