package com.example.mymoviddb.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.MovieModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.TVShowModel

@BindingAdapter("loadImage", "error")
fun loadImage(imageView: ImageView, uri: String?, error: Drawable) {
    val options: RequestOptions = RequestOptions()
        .error(error)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

    Glide.with(imageView.context)
        .load(BuildConfig.LOAD_POSTER_BASE_URL + uri)
        .apply(options)
        .into(imageView)
}

@BindingAdapter("attachShowList")
fun attachShowList(recyclerView: RecyclerView, showList: Result<*>?) {
    if (showList is Result.Success) {
        if (showList.data is MovieModel && !showList.data.results.isNullOrEmpty()) {
            val adapter = recyclerView.adapter as MoviesAdapter
            adapter.submitList(showList.data.results)
        } else if (showList.data is TVShowModel && !showList.data.results.isNullOrEmpty()) {
            val adapter = recyclerView.adapter as TVAdapter
            adapter.submitList(showList.data.results)
        }
    }
}