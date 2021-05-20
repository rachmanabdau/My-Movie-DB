package com.example.mymoviddb.core

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.core.utils.DeviceUtils

/**
 * Extend Linear Layout Manager to prefetch image in a list
 */
class PreloadLinearLayout(
    private val context: Context
) : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return DeviceUtils.getScreenWidth(context) * 4
    }
}