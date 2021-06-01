package com.example.mymoviddb.core.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.net.ParseException
import android.view.View
import com.example.mymoviddb.core.datasource.remote.moshi
import com.example.mymoviddb.core.model.ResponsedBackend
import com.example.mymoviddb.core.model.Result
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

object Util {

    /**
     * convert time stamp in string in format "yyyy-MM-dd HH:mm:ss"
     * to time in milli second
     *
     * [timeInString] time in string
     */
    fun convertStringTimeToMills(timeInString: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return try {
            val mDate: Date = sdf.parse(timeInString) as Date
            mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
            -1
        }
    }

    fun returnError(result: Response<*>): Result.Error {
        val errorAdapter = moshi.adapter(ResponsedBackend::class.java)
        val errorMessage = result.errorBody()?.string() ?: "Unknown error has occured"
        val errorJson =
            errorAdapter.fromJson(errorMessage)

        return Result.Error(Exception(errorJson?.statusMessage))
    }

    fun ObjectAnimator.disableViewDuringAnimation(view: View) {
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isClickable = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isClickable = true
            }

        })
    }
}