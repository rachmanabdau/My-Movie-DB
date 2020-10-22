package com.example.mymoviddb.authentication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mymoviddb.R
import com.example.mymoviddb.databinding.ActivityAuthenticationBinding
import com.example.mymoviddb.datasource.remote.RemoteServerAccess
import com.example.mymoviddb.model.Result

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    private val authenticationViewModel by viewModels<AuthenticationViewModel> {
        val remoteServer = RemoteServerAccess()
        AuthenticationViewModel.Facroty(remoteServer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)

        binding.authenticationVM = authenticationViewModel

        authenticationViewModel.loginGuestResult.observe(this, {
            when (it) {
                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(this.localClassName, "Error occured")
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d(this.localClassName, "Success occured")
                }
            }
        }
        )
    }
}