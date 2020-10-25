package com.example.mymoviddb.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.databinding.FragmentAuthenticationBinding
import com.example.mymoviddb.datasource.remote.RemoteServerAccess
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.EventObserver
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil

class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    private val authenticationViewModel by viewModels<AuthenticationViewModel> {
        val remoteServer = RemoteServerAccess()
        val app = requireActivity().application
        AuthenticationViewModel.Factory(app, remoteServer)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthenticationBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.authenticationVM = authenticationViewModel

        binding.loginWithAccount.setOnClickListener {
            findNavController().navigate(
                AuthenticationFragmentDirections.actionAuthenticationFragmentToLoginFragment()
            )
        }

        authenticationViewModel.loginGuestResult.observe(viewLifecycleOwner, EventObserver {
            if (it is Result.Success) {
                findNavController().navigate(
                    AuthenticationFragmentDirections.actionAuthenticationFragmentToHomeFragment()
                )
                PreferenceUtil.setAuthState(requireContext(), LoginState.AS_GUEST)
            }
        })

        return binding.root
    }
}