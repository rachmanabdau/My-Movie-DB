package com.example.mymoviddb.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.ResponsedBackend
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.UserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainAccess: IMainAccess) :
    ViewModel() {

    private val _userDetail = MutableLiveData<Result<UserDetail?>>()
    val userDetail: LiveData<Result<UserDetail?>> = _userDetail

    private val _logoutResult = MutableLiveData<Result<ResponsedBackend?>>()
    val logoutResult: LiveData<Result<ResponsedBackend?>> = _logoutResult

    fun getUserDetail(sessionId: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _userDetail.value = mainAccess.getUserDetail(sessionId, apiKey)
        }
    }

    fun logout(sessionId: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            val authData = HashMap<String, String>()
            authData.put("session_id", sessionId)
            _logoutResult.value = mainAccess.logout(authData, apiKey)
        }
    }


}