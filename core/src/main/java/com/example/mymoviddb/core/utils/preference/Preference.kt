package com.example.mymoviddb.core.utils.preference

interface Preference {
    fun setAuthState(state: LoginState)
    fun getAuthState(): Int
    fun writeGuestToken(token: String)
    fun readGuestToken(): String
    fun writeUserSession(token: String)
    fun readUserSession(): String
    fun writeGuestTokenExpiry(timeStamp: Long)
    fun readGuestTokenExpiry(): Long
    fun writeAccountId(accountId: Int)
    fun readAccountId(): Int
    fun logout()
}