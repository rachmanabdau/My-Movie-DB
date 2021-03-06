package com.example.mymoviddb.authentication.guest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.Event
import com.example.mymoviddb.utils.Util
import com.example.mymoviddb.utils.preference.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userPreference: Preference,
    private val remoteSource: IAuthenticationAccess
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
            if (isTokenValidAndAvailable(usePreferenceManager)) {
                val result = Event(remoteSource.loginAsGuest(apiKey))
                _loginGuestResult.value = result

                if (usePreferenceManager && result.peekContent() is Result.Success) {
                    val restultContent =
                        (result.peekContent() as Result.Success<GuestSessionModel?>)
                            .data as GuestSessionModel
                    saveTokenAndExpiry(restultContent)
                }
            } else {
                val token = userPreference.readGuestToken()
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
        userPreference.writeGuestToken(guestSession.guestSessionId)
        userPreference.writeGuestTokenExpiry(expiredDateInMillis)
    }

    private fun isTokenValidAndAvailable(usePreferenceManager: Boolean): Boolean {
        return if (usePreferenceManager) {
            val expireTime = userPreference.readGuestTokenExpiry()
            val currentTime = Util.getCurrenTimeGMT()
            expireTime < 0 && currentTime > expireTime
        } else {
            true
        }
    }
}