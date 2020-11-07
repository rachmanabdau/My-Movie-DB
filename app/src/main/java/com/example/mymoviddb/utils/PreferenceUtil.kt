package com.example.mymoviddb.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.preference.PreferenceManager


@SuppressLint("ApplySharedPref")
object PreferenceUtil {

    private const val GUEST_TOKEN_KEY =
        "package com.example.mymoviddb.utils.PreferenceUtil.guesttokenKey"
    private const val USER_TOKEN_KEY =
        "package com.example.mymoviddb.utils.PreferenceUtil.userTokenKey"
    private const val LOGIN_STATE_KEY =
        "package com.example.mymoviddb.utils.PreferenceUtil.loginStateKey"
    private const val TIME_STAMP_KEY =
        "package com.example.mymoviddb.utils.PreferenceUtil.timeStampKey"
    private const val ACCOUNT_ID_KEY =
        "package com.example.mymoviddb.utils.PreferenceUtil.accountIdKey"

    fun setAuthState(context: Context, state: LoginState) {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceEditor = tokenPreference.edit()
        preferenceEditor.putInt(LOGIN_STATE_KEY, state.stateId)
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

    fun writeUserSession(context: Context, token: String) {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceEditor = tokenPreference.edit()
        preferenceEditor.putString(USER_TOKEN_KEY, token)
        preferenceEditor.commit()
    }

    fun readUserSession(context: Context): String {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        return tokenPreference.getString(USER_TOKEN_KEY, "") ?: ""
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

    fun writeAccountId(context: Context, accountId: Int) {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val preferenceEditor = tokenPreference.edit()
        preferenceEditor.putInt(ACCOUNT_ID_KEY, accountId)
        preferenceEditor.commit()
    }

    fun readAccountId(context: Context): Int {
        val tokenPreference = PreferenceManager.getDefaultSharedPreferences(context)
        return tokenPreference.getInt(ACCOUNT_ID_KEY, -1)
    }
}

enum class LoginState(val stateId: Int) {
    AS_GUEST(1),
    AS_USER(2)
}