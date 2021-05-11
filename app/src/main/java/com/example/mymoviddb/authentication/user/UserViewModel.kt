package com.example.mymoviddb.authentication.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Event
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val access: IAuthenticationAccess,
    private val userPreference: UserPreference
) : ViewModel() {

    private val _loginResult = MutableLiveData<Event<String>>()
    val loginResult: LiveData<Event<String>> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val requestToken = getRequestToken()
            if (requestToken is Result.Success) {
                val login = access.loginAsUser(username, password, requestToken.data)
                if (login is Result.Success && login.data != null) {
                    val newSession = createNewSession(login.data.requestToken)
                    if (newSession is Result.Success && newSession.data != null) {
                        userPreference.writeUserSession(newSession.data.sessionId)
                        userPreference.setAuthState(LoginState.AS_USER)
                        _loginResult.value = Event("success")
                    } else if (newSession is Result.Error) {
                        _loginResult.value = Event(newSession.exception.localizedMessage)
                    }
                } else if (login is Result.Error) {
                    _loginResult.value = Event(login.exception.localizedMessage)
                }
            } else if (requestToken is Result.Error) {
                _loginResult.value = Event(requestToken.exception.localizedMessage)
            }
        }
    }

    suspend fun getRequestToken() = access.getRequestToken(BuildConfig.V3_AUTH)
    suspend fun createNewSession(requestToken: String) =
        access.createNewSession(requestToken, BuildConfig.V3_AUTH)
}