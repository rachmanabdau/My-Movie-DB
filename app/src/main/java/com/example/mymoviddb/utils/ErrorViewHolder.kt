package com.example.mymoviddb.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.databinding.TryAgainLoadListBinding


class ErrorViewHolder(private val binding: TryAgainLoadListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(errorMessage: String, action: () -> Unit) {
        binding.errorMessage.text = errorMessage
        binding.tryAgainButton.setOnClickListener {
            binding.errorMessage.visibility = View.GONE
            binding.tryAgainButton.visibility = View.GONE
            binding.retryLoading.visibility = View.VISIBLE
            action()
            binding.errorMessage.visibility = View.VISIBLE
            binding.tryAgainButton.visibility = View.VISIBLE
            binding.retryLoading.visibility = View.GONE
        }
    }
}