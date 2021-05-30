package com.example.mymoviddb.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.R
import com.example.mymoviddb.core.utils.EventObserver
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.UserPreference
import com.example.mymoviddb.databinding.FragmentLoginBinding
import com.example.mymoviddb.home.HomeFragment
import com.example.mymoviddb.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val loginViewModel by activityViewModels<LoginViewModel>()

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.loginToolbar.toolbar.title = getString(R.string.action_login)

        setLoginStateToHome(LoginState.NOT_LOGIN)
        setupButtonClick()
        observeLoginResult()
        setUpBackPressed()

        return binding.root
    }

    private fun setUpBackPressed() {
        // close activity if back button is pressed instead of navigating back to home
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }

    private fun setupButtonClick() {
        binding.login.setOnClickListener {
            if (isAllFieldValid()) {
                val username = binding.usernameInputText.text.toString()
                val password = binding.passwordInputText.text.toString()
                setLoadingStateView(true)
                loginViewModel.login(username, password)
            }
        }

        binding.loginAsGuest.setOnClickListener {
            setLoginStateToHome(LoginState.AS_GUEST)
            findNavController().popBackStack()
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
        loginViewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            val isLoginSuccess = it.equals("success", true)
            if (isLoginSuccess) {
                userPreference.setAuthState(LoginState.AS_USER)
                restartActivity()
            } else {
                showErrorMessage(it)
            }
            setLoadingStateView(false)
        })
    }

    private fun setLoginStateToHome(state: LoginState) {
        userPreference.setAuthState(state)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(
            HomeFragment.loginStateKey,
            state.stateId
        )
    }

    private fun setLoadingStateView(isLoading: Boolean) {
        binding.login.isEnabled = !isLoading
        binding.loginAsGuest.isEnabled = !isLoading
        binding.loginProgress.isVisible = isLoading
    }

    private fun restartActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}