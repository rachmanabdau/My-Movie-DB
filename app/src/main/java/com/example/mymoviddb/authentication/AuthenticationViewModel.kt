package com.example.mymoviddb.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.datasource.remote.RemoteServer
import com.example.mymoviddb.model.GuestSessionModel
import com.example.mymoviddb.model.Result
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val remoteSource: RemoteServer) : ViewModel() {

    private val _loginGuestResult = MutableLiveData<Result<GuestSessionModel?>>()
    val loginGuestResult: LiveData<Result<GuestSessionModel?>> = _loginGuestResult

    fun loginAsGuest() {
        viewModelScope.launch {
            _loginGuestResult.value = Result.Loading
            _loginGuestResult.value = remoteSource.loginAsGuest()
        }
    }
}