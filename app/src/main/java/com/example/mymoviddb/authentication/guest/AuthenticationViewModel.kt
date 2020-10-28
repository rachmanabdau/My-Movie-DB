package com.example.mymoviddb.authentication.guest

import android.app.Application
import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Event
import com.example.mymoviddb.utils.PreferenceUtil
import com.example.mymoviddb.utils.Util
import kotlinx.coroutines.launch

class AuthenticationViewModel(
    private val app: Application,
    private val remoteSource: RemoteServer
) : ViewModel() {

    private val _loginGuestResult = MutableLiveData<Event<Result<GuestSessionModel?>>>()
    val loginGuestResult: LiveData<Event<Result<GuestSessionModel?>>> = _loginGuestResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = false
    }

    @JvmOverloads
    fun loginAsGuest(usePreferenceManager: Boolean = true, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _isLoading.value = true
            if (isTokenValidAndAvailable()) {
                val result = Event(remoteSource.loginAsGuest(apiKey))
                _loginGuestResult.value = result

                if (usePreferenceManager && result.peekContent() is Result.Success) {
                    val restultContent =
                        (result.peekContent() as Result.Success<GuestSessionModel?>)
                            .data as GuestSessionModel
                    saveTokenAndExpiry(restultContent)
                }
            } else {
                val token = PreferenceUtil.readGuestToken(app)
                _loginGuestResult.value = Event(
                    Result.Success(
                        GuestSessionModel(
                            guestSessionId = token, success = true, expiresAt = ""
                        )
                    )
                )
            }
            _isLoading.value = false
        }
    }

    private fun saveTokenAndExpiry(guestSession: GuestSessionModel) {
        val expiredDate = guestSession.expiresAt.replace(" UTC", "", true)
        val expiredDateInMillis = Util.convertStringTimeToMills(expiredDate)
        PreferenceUtil.writeGuestToken(app, guestSession.guestSessionId)
        PreferenceUtil.writeGuestTokenExpiry(app, expiredDateInMillis)
    }

    private fun isTokenValidAndAvailable(): Boolean {
        val expireTime = PreferenceUtil.readGuestTokenExpiry(app)
        val currentTime = Util.getCurrenTimeGMT()
        return expireTime < 0 && currentTime > expireTime
    }

    class Factory(
        private val app: Application,
        private val remoteSource: RemoteServer
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
                return AuthenticationViewModel(app, remoteSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}