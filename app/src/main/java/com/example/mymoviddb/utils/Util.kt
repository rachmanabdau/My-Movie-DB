package com.example.mymoviddb.utils

import android.net.ParseException
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
}