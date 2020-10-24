package com.example.mymoviddb.utils

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.adapters.MoviesAdapter
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.google.android.material.snackbar.Snackbar


@BindingAdapter("showLoadingState")
fun showProgressbar(view: ProgressBar, result: Result<*>?) {
    view.visibility = when (result) {
        is Result.Loading -> View.VISIBLE
        is Result.Error -> {
            val errorMessage = result.exception.localizedMessage as String
            Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show()
            View.GONE
        }
        else -> {
            View.GONE
        }
    }
}

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

@BindingAdapter("attachList")
fun attachMovieList(recyclerView: RecyclerView, movieList: Result<MovieModel>?) {
    if (movieList is Result.Success && !movieList.data.results.isNullOrEmpty()) {
        val adapter = recyclerView.adapter as MoviesAdapter
        adapter.submitList(movieList.data.results)
    }
}