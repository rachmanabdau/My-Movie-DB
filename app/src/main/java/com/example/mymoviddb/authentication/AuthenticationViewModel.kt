package com.example.mymoviddb.authentication

import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val remoteSource: RemoteServer) : ViewModel() {

    private val _loginGuestResult = MutableLiveData<Result<GuestSessionModel?>>()
    val loginGuestResult: LiveData<Result<GuestSessionModel?>> = _loginGuestResult

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean> = _isButtonEnabled

    init {
        _isButtonEnabled.value = true
    }

    @JvmOverloads
    fun loginAsGuest(apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _loginGuestResult.value = Result.Loading
            _isButtonEnabled.value = false
            delay(2000)
            _loginGuestResult.value = remoteSource.loginAsGuest(apiKey)
            _isButtonEnabled.value = true
        }
    }

    class Facroty(
        private val remoteSource: RemoteServer
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
                return AuthenticationViewModel(remoteSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}