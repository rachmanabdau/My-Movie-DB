package com.example.mymoviddb.home

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymoviddb.core.utils.DeviceUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Extend Linear Layout Manager to prefetch image in a list
 */
//@WithFragmentBindings
class PreloadLinearLayout @Inject constructor(
    @ApplicationContext private val context: Context
) : LinearLayoutManager(context, RecyclerView.HORIZONTAL, false) {

    override fun getExtraLayoutSpace(state: RecyclerView.State): Int {
        return DeviceUtils.getScreenWidth(context) * 4
    }
}