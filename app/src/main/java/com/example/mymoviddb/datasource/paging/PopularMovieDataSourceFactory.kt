package com.example.mymoviddb.datasource.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.MovieModel
import kotlinx.coroutines.CoroutineScope

class PopularMovieDataSourceFactory(
    private val networkService: RemoteServer,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MovieModel.Result>() {

    val sourceLiveData = MutableLiveData<PopularMovieDataSource>()

    override fun create(): DataSource<Int, MovieModel.Result> {
        val source = PopularMovieDataSource(networkService, scope)
        sourceLiveData.postValue(source)
        return source
    }

}