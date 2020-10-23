package com.example.mymoviddb.home

import android.app.Application
import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val app: Application,
    private val renoteSource: RemoteServer
) : AndroidViewModel(app) {

    private val _movieList = MutableLiveData<Result<MovieModel?>>()
    val movieList: LiveData<Result<MovieModel?>> = _movieList

    @JvmOverloads
    fun getMovieList(apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _movieList.value = Result.Loading
            delay(2000)
            _movieList.value = renoteSource.getPopularMvoieList(apiKey)
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