package com.example.mymoviddb.home

import android.app.Application
import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.launch

class HomeViewModel(
    private val app: Application,
    private val renoteSource: RemoteServer
) : AndroidViewModel(app) {

    private val _popularMovieList = MutableLiveData<Result<MovieModel?>>()
    val popularMovieList: LiveData<Result<MovieModel?>> = _popularMovieList

    private val _nowPlayingMovieList = MutableLiveData<Result<MovieModel?>>()
    val nowPlayingMovieList: LiveData<Result<MovieModel?>> = _nowPlayingMovieList

    init {
        getPopularMovieList()
    }

    @JvmOverloads
    fun getPopularMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _popularMovieList.value = Result.Loading
            _popularMovieList.value = renoteSource.getPopularMovieList(page, apiKey)
        }
    }

    fun getBowPlayingMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _nowPlayingMovieList.value = renoteSource.getNowPlayingMovieList(page, apiKey)
        }
    }

    class Factory(
        private val app: Application,
        private val remoteSource: RemoteServer
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                return HomeViewModel(app, remoteSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}