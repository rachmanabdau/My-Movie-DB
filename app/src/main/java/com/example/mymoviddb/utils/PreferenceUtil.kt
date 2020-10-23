package com.example.mymoviddb.utils

import android.app.Activity
import android.content.Context

object PreferenceUtil {

    private val GUEST_TOKEN_KEY = "package com.example.mymoviddb.utils.PreferenceUtil.tokenKey"

    fun writeGuestToken(activity: Activity?, token: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(GUEST_TOKEN_KEY, token)
            commit()
        }

    }

    fun readGuestToken(activity: Activity?): String {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        return sharedPref?.getString(GUEST_TOKEN_KEY, "") ?: ""
    }
}