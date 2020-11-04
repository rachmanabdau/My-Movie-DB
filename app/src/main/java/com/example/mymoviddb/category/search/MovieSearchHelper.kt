package com.example.mymoviddb.category.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mymoviddb.category.movie.ICategoryMovieListAccess
import com.example.mymoviddb.category.movie.MovieDataSource
import com.example.mymoviddb.category.movie.MovieDataSourceFactory
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.CoroutineScope

class MovieSearchHelper(
    movieListAccess: ICategoryMovieListAccess,
    scope: CoroutineScope,
    title: String
) {

    val dataSourceFactory = MovieDataSourceFactory(movieListAccess, scope, title)

    fun getPageList(config: PagedList.Config): LiveData<PagedList<MovieModel.Result>> {
        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun getResult(): LiveData<Result<MovieModel?>> {
        return Transformations.switchMap(
            dataSourceFactory.sourceLiveData,
            MovieDataSource::result
        )
    }

    fun getRetry() {
        dataSourceFactory.sourceLiveData.value?.retry()
    }
}