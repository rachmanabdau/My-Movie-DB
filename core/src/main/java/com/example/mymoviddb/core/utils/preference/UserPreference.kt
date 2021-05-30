package com.example.mymoviddb.core.utils.preference

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import javax.inject.Inject


class UserPreference @Inject constructor(application: Application) : Preference {

    private val GUEST_TOKEN_KEY =
        "package com.example.mymoviddb.core.utils.preference.UserPreference.guesttokenKey"
    private val USER_TOKEN_KEY =
        "package com.example.mymoviddb.core.utils.preference.UserPreference.userTokenKey"
    private val LOGIN_STATE_KEY =
        "package com.example.mymoviddb.core.utils.preference.UserPreference.loginStateKey"
    private val TIME_STAMP_KEY =
        "package com.example.mymoviddb.core.utils.preference.UserPreference.timeStampKey"
    private val ACCOUNT_ID_KEY =
        "package com.example.mymoviddb.core.utils.preference.UserPreference.accountIdKey"

    val sharedPrefsFile: String =
        "package com.example.mymoviddb.core.utils.preference.UserPreference"
    val masterKey = MasterKey.Builder(application)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences = EncryptedSharedPreferences.create(
        application,
        sharedPrefsFile,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun setAuthState(state: LoginState) {
        val preferenceEditor = sharedPreferences.edit()
        preferenceEditor.putInt(LOGIN_STATE_KEY, state.stateId)
        preferenceEditor.apply()
    }

    override fun getAuthState(): Int {
        return sharedPreferences.getInt(LOGIN_STATE_KEY, LoginState.NOT_LOGIN.stateId)
    }

    override fun writeGuestToken(token: String) {
        val preferenceEditor = sharedPreferences.edit()
        preferenceEditor.putString(GUEST_TOKEN_KEY, token)
        preferenceEditor.apply()
    }

    override fun readGuestToken(): String {
        return sharedPreferences.getString(GUEST_TOKEN_KEY, "") ?: ""
    }

    override fun writeUserSession(token: String) {
        val preferenceEditor = sharedPreferences.edit()
        preferenceEditor.putString(USER_TOKEN_KEY, token)
        preferenceEditor.apply()
    }

    override fun readUserSession(): String {
        return sharedPreferences.getString(USER_TOKEN_KEY, "") ?: ""
    }

    override fun writeGuestTokenExpiry(timeStamp: Long) {
        val preferenceEditor = sharedPreferences.edit()
        preferenceEditor.putLong(TIME_STAMP_KEY, timeStamp)
        preferenceEditor.apply()
    }

    override fun readGuestTokenExpiry(): Long {
        return sharedPreferences.getLong(TIME_STAMP_KEY, -1)
    }

    override fun writeAccountId(accountId: Int) {
        val preferenceEditor = sharedPreferences.edit()
        preferenceEditor.putInt(ACCOUNT_ID_KEY, accountId)
        preferenceEditor.apply()
    }

    override fun readAccountId(): Int {
        return sharedPreferences.getInt(ACCOUNT_ID_KEY, -1)
    }

    override fun logout() {
        sharedPreferences.edit().clear().apply()
    }
}

enum class LoginState(val stateId: Int) {
    NOT_LOGIN(0),
    AS_GUEST(1),
    AS_USER(2)
}