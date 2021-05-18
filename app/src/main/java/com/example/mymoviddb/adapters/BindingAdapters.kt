package com.example.mymoviddb.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.ShowResponse

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
fun attachShowList(recyclerView: RecyclerView, showList: Result<ShowResponse>?) {
    if (showList is Result.Success) {
        val adapter = recyclerView.adapter as PreviewShowAdapter
        adapter.submitList(showList.data.results)
    }
}