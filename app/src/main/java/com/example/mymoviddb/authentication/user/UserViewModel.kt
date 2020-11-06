package com.example.mymoviddb.authentication.user

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Event
import com.example.mymoviddb.utils.PreferenceUtil
import kotlinx.coroutines.launch
import timber.log.Timber

class UserViewModel @ViewModelInject constructor(
    private val access: IAuthenticationAccess,
    private val app: Application
) : AndroidViewModel(app) {

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
                        PreferenceUtil.writeUserSession(app, newSession.data.sessionId)
                        _loginResult.value = Event("success")
                    } else if (newSession is Result.Error) {
                        Timber.d("error occured in request new session")
                        _loginResult.value = Event(newSession.exception.localizedMessage)
                    }
                } else if (login is Result.Error) {
                    Timber.d("error occured in request login")
                    _loginResult.value = Event(login.exception.localizedMessage)
                }
            } else if (requestToken is Result.Error) {
                Timber.d("error occured in request token")
                _loginResult.value = Event(requestToken.exception.localizedMessage)
            }
        }
    }

    suspend fun getRequestToken() = access.getRequestToken(BuildConfig.V3_AUTH)
    suspend fun createNewSession(requestToken: String) =
        access.createNewSession(requestToken, BuildConfig.V3_AUTH)
}