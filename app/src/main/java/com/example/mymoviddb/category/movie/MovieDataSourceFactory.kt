package com.example.mymoviddb.category.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mymoviddb.model.MovieModel
import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
    private val networkService: ICategoryMovieListAccess,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, MovieModel.Result>() {

    val sourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieModel.Result> {
        val source = MovieDataSource(networkService, scope)
        sourceLiveData.postValue(source)
        return source
    }

}