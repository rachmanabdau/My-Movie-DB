package com.example.mymoviddb.core.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.core.databinding.TryAgainLoadListBinding
import com.example.mymoviddb.core.model.Result


class ErrorViewHolder(private val binding: TryAgainLoadListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(state: Result<*>, errorMessage: String, action: () -> Unit) {
        binding.errorMessage.text = errorMessage

        binding.errorMessage.visibility = if (state is Result.Error) View.VISIBLE else View.GONE
        binding.tryAgainButton.visibility = if (state is Result.Error) View.VISIBLE else View.GONE
        binding.retryLoading.visibility = if (state is Result.Loading) View.VISIBLE else View.GONE

        binding.tryAgainButton.setOnClickListener {
            action()
        }
    }
}