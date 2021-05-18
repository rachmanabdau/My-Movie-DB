package com.example.mymoviddb.core.utils

import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.core.databinding.LoadMoreItemBinding

class LoadMoreViewHolder(private val binding: LoadMoreItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(action: (() -> Unit)?) {
        binding.loadmoreContainer.setOnClickListener {
            action?.invoke()
        }
    }
}