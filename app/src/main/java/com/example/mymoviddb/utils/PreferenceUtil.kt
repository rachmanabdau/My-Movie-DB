package com.example.mymoviddb.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager


@SuppressLint("ApplySharedPref")
object PreferenceUtil {

    private val GUEST_TOKEN_KEY = "package com.example.mymoviddb.utils.PreferenceUtil.guesttokenKey"
    private val LOGIN_STATE_KEY = "package com.example.mymoviddb.utils.PreferenceUtil.loginStateKey"
    private val TIME_STAMP_KEY = "package com.example.mymoviddb.utils.PreferenceUtil.timeStampKey"

    fun setAuthState(context: Context, state: LoginState) {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceEditor = tokenPreference.edit()
        preferenceEditor.putInt(LOGIN_STATE_KEY, state.ordinal)
        preferenceEditor.commit()
    }

    fun getAuthState(context: Context): Int {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        return tokenPreference.getInt(LOGIN_STATE_KEY, -1)
    }

    fun writeGuestToken(context: Context, token: String) {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceEditor = tokenPreference.edit()
        preferenceEditor.putString(GUEST_TOKEN_KEY, token)
        preferenceEditor.commit()
    }

    fun readGuestToken(context: Context): String {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        return tokenPreference.getString(GUEST_TOKEN_KEY, "") ?: ""
    }

    fun writeGuestTokenExpiry(context: Context, timeStamp: Long) {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceEditor = tokenPreference.edit()
        preferenceEditor.putLong(TIME_STAMP_KEY, timeStamp)
        preferenceEditor.commit()
    }

    fun readGuestTokenExpiry(context: Context): Long {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        return tokenPreference.getLong(TIME_STAMP_KEY, -1)
    }
}

enum class LoginState(stateId: Int = 1) {
    AS_GUEST(1),
    AS_USER(2)
}