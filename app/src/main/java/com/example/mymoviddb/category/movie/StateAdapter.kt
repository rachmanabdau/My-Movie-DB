package com.example.mymoviddb.category.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mymoviddb.databinding.TryAgainLoadListBinding

class StateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ErrorViewHolderV3>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ErrorViewHolderV3 {
        val viewbinding =
            TryAgainLoadListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ErrorViewHolderV3(viewbinding, retry)
    }

    override fun onBindViewHolder(
        holder: ErrorViewHolderV3,
        loadState: LoadState
    ) {
        if (loadState is LoadState.Loading || loadState is LoadState.Error) {
            // Set loading or error placeholder to full span (current span is 2)
            val staggaredLayoutParam =
                holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            staggaredLayoutParam.isFullSpan = true
        }
        holder.onBind(loadState)
    }
}

class ErrorViewHolderV3(
    private val binding: TryAgainLoadListBinding, private val retry: () -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {


    fun onBind(state: LoadState) {
        if (state is LoadState.Error) {
            binding.errorMessage.text = state.error.localizedMessage
        }

        binding.errorMessage.isVisible = state is LoadState.Error
        binding.tryAgainButton.isVisible = state is LoadState.Error
        binding.retryLoading.isVisible = state is LoadState.Loading

        binding.tryAgainButton.setOnClickListener {
            retry()
        }
    }
}
