package com.example.mymoviddb.authentication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.LoginTokenModel
import com.example.mymoviddb.core.model.NewSessionModel
import com.example.mymoviddb.core.model.RequestTokenModel
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.utils.Event
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val access: IAuthenticationAccess,
    private val userPreference: Preference
) : ViewModel() {

    private val _loginResult = MutableLiveData<Event<String>>()
    val loginResult: LiveData<Event<String>> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val requestToken = getRequestToken()
            if (requestToken is Result.Success) {
                login(username, password, requestToken.data)
            } else if (requestToken is Result.Error) {
                _loginResult.value = Event(setErrorMessage(requestToken))
            }
        }
    }

    private fun setErrorMessage(result: Result<*>): String {
        return (result as Result.Error).exception.localizedMessage ?: "Unknown error occurred."
    }

    private suspend fun getRequestToken() = access.getRequestToken(BuildConfig.V3_AUTH)
    private suspend fun createNewSession(requestToken: String) =
        access.createNewSession(requestToken, BuildConfig.V3_AUTH)

    private suspend fun login(username: String, password: String, token: RequestTokenModel?) {
        val login = access.loginAsUser(username, password, token)
        if (login is Result.Success) {
            login.data?.let { createSession(it) }
        } else if (login is Result.Error) {
            _loginResult.value = Event(setErrorMessage(login))
        }
    }

    private suspend fun createSession(loginResult: LoginTokenModel) {
        val newSession = createNewSession(loginResult.requestToken)
        if (newSession is Result.Success) {
            newSession.data?.let { updateSession(it) }
            _loginResult.value = Event("success")
        } else if (newSession is Result.Error) {
            _loginResult.value = Event(setErrorMessage(newSession))
        }
    }

    private fun updateSession(newSession: NewSessionModel) {
        userPreference.writeUserSession(newSession.sessionId)
        userPreference.setAuthState(LoginState.AS_USER)
    }
}