package com.example.mymoviddb.category.tv

import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

interface ICategoryTVListAccess {

    suspend fun getPopularTvShowList(page: Int, apiKey: String): Result<TVShowModel?>

    suspend fun getOnAirTvShowList(page: Int, apiKey: String): Result<TVShowModel?>

    suspend fun searchTvShowList(title: String, page: Int, apiKey: String): Result<TVShowModel?>
}