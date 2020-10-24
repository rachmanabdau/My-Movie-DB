package com.example.mymoviddb.utils

import android.app.Activity
import android.content.Context


object PreferenceUtil {

    private val GUEST_TOKEN_KEY = "package com.example.mymoviddb.utils.PreferenceUtil.guesttokenKey"
    private val LOGIN_STATE_KEY = "package com.example.mymoviddb.utils.PreferenceUtil.loginStateKey"

    fun setAuthState(activity: Activity?, state: LoginState) {
        val loginStatePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(loginStatePref.edit()) {
            putInt(LOGIN_STATE_KEY, state.ordinal)
            commit()
        }
    }

    fun setAuthState(activity: Activity?): Int {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        return sharedPref?.getInt(LOGIN_STATE_KEY, 0) ?: 0
    }

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

enum class LoginState(stateId: Int) {
    NOT_LOG_IN(0),
    AS_GUEST(1),
    AS_USER(2)
}