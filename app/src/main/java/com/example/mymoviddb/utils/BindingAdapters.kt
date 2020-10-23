package com.example.mymoviddb.utils

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.example.mymoviddb.model.Result
import com.google.android.material.snackbar.Snackbar

@BindingAdapter("showLoadingState")
fun showProgressbar(view: ProgressBar, result: Result<*>?) {
    view.visibility = when (result) {
        is Result.Loading -> View.VISIBLE
        is Result.Success -> {
            View.GONE
        }
        is Result.Error -> {
            val errorMessage = result.exception.localizedMessage as String
            Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show()
            View.GONE
        }
        null -> {
            View.GONE
        }
    }
}