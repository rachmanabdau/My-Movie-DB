package com.example.mymoviddb.authentication.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mymoviddb.databinding.FragmentUserBinding
import com.example.mymoviddb.main.MainActivity
import com.example.mymoviddb.utils.EventObserver
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            if (isAllFieldValid()) {
                val username = binding.usernameInputText.text.toString()
                val password = binding.passwordInputText.text.toString()
                binding.login.isEnabled = false
                userViewModel.login(username, password)
            }
        }

        userViewModel.loginResult.observe(viewLifecycleOwner, EventObserver {
            if (it.equals("success", true)) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
                binding.login.isEnabled = true
            }
        })

        return binding.root
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
}