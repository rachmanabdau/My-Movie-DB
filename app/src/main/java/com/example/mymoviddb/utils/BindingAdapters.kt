package com.example.mymoviddb.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.adapters.MoviesAdapter
import com.example.mymoviddb.adapters.TVAdapter
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

@BindingAdapter("loadImage", "error")
fun loadImage(imageView: ImageView, uri: String?, error: Drawable) {
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .error(error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    Glide.with(imageView.context)
        .load(BuildConfig.LOAD_POSTER_BASE_URL + uri)
        .apply(options)
        .into(imageView)
}

@BindingAdapter("attachMovieList")
fun attachMovieList(recyclerView: RecyclerView, movieList: Result<MovieModel>?) {
    if (movieList is Result.Success && !movieList.data.results.isNullOrEmpty()) {
        val adapter = recyclerView.adapter as MoviesAdapter
        adapter.submitList(movieList.data.results)
    }
}

@BindingAdapter("attachTVList")
fun attachTVList(recyclerView: RecyclerView, tvList: Result<TVShowModel>?) {
    if (tvList is Result.Success && !tvList.data.results.isNullOrEmpty()) {
        val adapter = recyclerView.adapter as TVAdapter
        adapter.submitList(tvList.data.results)
    }
}
