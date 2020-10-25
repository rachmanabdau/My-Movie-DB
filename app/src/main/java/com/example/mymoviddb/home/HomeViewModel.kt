package com.example.mymoviddb.home

import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel
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

    init {
        getPopularMovieList()
    }

    @JvmOverloads
    fun getPopularMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            println("api key : $apiKey")
            _popularMovieList.value = renoteSource.getPopularMovieList(page, apiKey)
        }
    }

    fun getBowPlayingMovieList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _nowPlayingMovieList.value = renoteSource.getNowPlayingMovieList(page, apiKey)
        }
    }

    fun getPopularTVList(page: Int = 1, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _popularTVList.value = renoteSource.getPopularTvShowList(page, apiKey)
        }
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