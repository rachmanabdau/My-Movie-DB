package com.example.mymoviddb.authentication.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mymoviddb.core.utils.EventObserver
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.UserPreference
import com.example.mymoviddb.databinding.FragmentUserBinding
import com.example.mymoviddb.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    private val userViewModel by viewModels<UserViewModel>()

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        setLoginButtonClick()
        observeLoginResult()

        return binding.root
    }

    private fun setLoginButtonClick() {
        binding.login.setOnClickListener {
            if (isAllFieldValid()) {
                val username = binding.usernameInputText.text.toString()
                val password = binding.passwordInputText.text.toString()
                binding.login.isEnabled = false
                userViewModel.login(username, password)
                binding.loginProgress.visibility = View.VISIBLE
            }
        }
    }

    private fun isAllFieldValid(): Boolean {
        val isUsernameValid = binding.usernameInputText.text.toString().isNotBlank()
        val isPasswordValid = binding.passwordInputText.text.toString().isNotBlank()

        binding.usernameInputLayout.error =
            if (!isUsernameValid) "Please enter your username" else null
        binding.passwordInputLayout.error =
            if (!isPasswordValid) "Please enter your password" else null

        return isUsernameValid && isPasswordValid
    }

    private fun observeLoginResult() {
        userViewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            val isLoginSuccess = it.equals("success", true)
            if (isLoginSuccess) {
                userPreference.setAuthState(LoginState.AS_USER)
                navigateToMainActivity()
            } else {
                binding.login.isEnabled = true
                showErrorMessage(it)
            }
            binding.loginProgress.visibility = View.GONE
        })
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}