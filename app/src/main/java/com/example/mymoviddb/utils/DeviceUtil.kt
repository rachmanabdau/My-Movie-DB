package com.example.mymoviddb.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowInsets
import android.view.WindowManager


/**
 * Object used to get width or height screen so linear layout can
 * prefetch loading image
 */
object DeviceUtils {
    /**
     * param context
     * @return the screen height in pixels
     */
    /*fun getScreenHeight(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (android.os.Build.VERSION.SDK_INT < 30) {
            @Suppress("DEPRECATION")
            val display = windowManager.defaultDisplay
            val size = Point()
            @Suppress("DEPRECATION")
            display.getSize(size)
            size.y
        } else {
            val metrics = windowManager.currentWindowMetrics
            // Gets all excluding insets
            val windowInsets = metrics.windowInsets
            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )

            insets.top + insets.bottom
        }
    }*/

    /**
     * @param context
     * @return the screen width in pixels
     */
    fun getScreenWidth(context: Context): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

        return if (android.os.Build.VERSION.SDK_INT < 30) {
            @Suppress("DEPRECATION")
            val display = windowManager.defaultDisplay
            val size = Point()
            @Suppress("DEPRECATION")
            display.getSize(size)
            size.x
        } else {
            val metrics = windowManager.currentWindowMetrics
            // Gets all excluding insets
            val windowInsets = metrics.windowInsets
            val insets = windowInsets.getInsetsIgnoringVisibility(
                WindowInsets.Type.navigationBars()
                        or WindowInsets.Type.displayCutout()
            )
            insets.right + insets.left
        }
    }
}