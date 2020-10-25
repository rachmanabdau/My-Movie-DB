package com.example.mymoviddb.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Extend Linear Layout Manager to prefetch image in a list
 */
class PreloadLinearLayout(
    context: Context,
    listOrientation: Int = RecyclerView.VERTICAL,
    isLayoutReversed: Boolean
) : LinearLayoutManager(context, listOrientation, isLayoutReversed) {

    private var extraLayoutSpace = 600

    fun setExtraLayoutSpace(extraLayoutSpace: Int = 600) {
        this.extraLayoutSpace = extraLayoutSpace
    }

    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return extraLayoutSpace
    }
}