package com.example.mymoviddb.core.mock

import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.Preference

class FakeUserPreference : Preference {

    private var loginState = LoginState.AS_GUEST
    private var guestToken = ""
    private var session = FakeDataFactory.sampleSessionId
    private var guestTokenExpiry = 0L
    private var accountId = 0

    override fun setAuthState(state: LoginState) {
        loginState = state
    }

    override fun getAuthState(): Int {
        return loginState.stateId
    }

    override fun writeGuestToken(token: String) {
        guestToken = token
    }

    override fun readGuestToken(): String {
        return guestToken
    }

    override fun writeUserSession(token: String) {
        session = token
    }

    override fun readUserSession(): String {
        return session
    }

    override fun writeGuestTokenExpiry(timeStamp: Long) {
        guestTokenExpiry = timeStamp
    }

    override fun readGuestTokenExpiry(): Long {
        return guestTokenExpiry
    }

    override fun writeAccountId(accountId: Int) {
        this.accountId = accountId
    }

    override fun readAccountId(): Int {
        return accountId
    }

    override fun logout() {
    }
}