package com.example.mymoviddb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.core.databinding.LoadingPlaceholderItemBinding

class PlaceHolderAdapter : RecyclerView.Adapter<DummyPlaceHolder>() {

    val dummyList = mutableListOf<Int>().also { element ->
        repeat(20) {
            element.add(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DummyPlaceHolder {
        val view = LoadingPlaceholderItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DummyPlaceHolder(view)
    }

    override fun onBindViewHolder(holder: DummyPlaceHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return dummyList.size
    }
}

class DummyPlaceHolder(binding: LoadingPlaceholderItemBinding) :
    RecyclerView.ViewHolder(binding.root)