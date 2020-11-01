package com.example.mymoviddb.utils

import android.net.ParseException
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mymoviddb.datasource.remote.moshi
import com.example.mymoviddb.model.Error401Model
import com.example.mymoviddb.model.Result
import com.google.android.material.appbar.MaterialToolbar
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

    /**
     * method to get current time at GMT+0 returns Long
     */
    fun getCurrenTimeGMT(): Long {
        val gmtDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        gmtDateFormat.timeZone = TimeZone.getTimeZone("GMT")
        //Current Date Time in GMT
        return convertStringTimeToMills(gmtDateFormat.format(Date()))
    }

    fun returnError(result: Response<*>): Result.Error {
        val errorAdapter = moshi.adapter(Error401Model::class.java)
        val errorMessage = result.errorBody()?.string() ?: "Unknown error has occured"
        val errorJson =
            errorAdapter.fromJson(errorMessage)

        return Result.Error(Exception(errorJson?.statusMessage))
    }

    /**
     * This function used in a helper to et up toolbar in each fragment
     */
    fun setupToolbar(toolbar: MaterialToolbar, navcontroller: NavController) {
        val appBarConfiguration = AppBarConfiguration(navcontroller.graph)
        toolbar.setupWithNavController(navcontroller, appBarConfiguration)
    }
}