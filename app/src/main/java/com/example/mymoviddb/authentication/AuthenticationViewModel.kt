package com.example.mymoviddb.authentication

import androidx.lifecycle.*
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val remoteSource: RemoteServer) : ViewModel() {

    private val _loginGuestResult = MutableLiveData<Result<GuestSessionModel?>>()
    val loginGuestResult: LiveData<Result<GuestSessionModel?>> = _loginGuestResult

    fun loginAsGuest(apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _loginGuestResult.value = Result.Loading
            _loginGuestResult.value = remoteSource.loginAsGuest(apiKey)
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