package com.example.mymoviddb.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieDetail
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVDetail
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(private val detailaAccess: IDetailAccess) :
    ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetail?>>()
    val movieDetail: LiveData<Result<MovieDetail?>> = _movieDetail

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
}
