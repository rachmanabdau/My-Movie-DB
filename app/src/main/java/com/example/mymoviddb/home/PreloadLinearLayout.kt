package com.example.mymoviddb.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.utils.DeviceUtils

/**
 * Extend Linear Layout Manager to prefetch image in a list
 */
class PreloadLinearLayout(
    private val context: Context,
    listOrientation: Int = RecyclerView.VERTICAL,
    isLayoutReversed: Boolean
) : LinearLayoutManager(context, listOrientation, isLayoutReversed) {

    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return DeviceUtils.getScreenWidth(context) * 4
    }
}