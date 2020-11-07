package com.example.mymoviddb.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.model.UserDetail
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val mainAccess: IMainAccess) :
    ViewModel() {

    private val _userDetail = MutableLiveData<Result<UserDetail?>>()
    val userDetail: LiveData<Result<UserDetail?>> = _userDetail

    fun getUserDetail(sessionId: String, apiKey: String = BuildConfig.V3_AUTH) {
        viewModelScope.launch {
            _userDetail.value = mainAccess.getUserDetail(sessionId, apiKey)
        }
    }
}