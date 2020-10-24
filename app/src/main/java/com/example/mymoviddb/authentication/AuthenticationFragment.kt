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
import com.example.mymoviddb.utils.PreferenceUtil

class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    private val authenticationViewModel by viewModels<AuthenticationViewModel> {
        val remoteServer = RemoteServerAccess()
        AuthenticationViewModel.Factory(remoteServer)
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

        // When login as gues success, navigate to home fragment
        authenticationViewModel.loginGuestResult.observe(viewLifecycleOwner, EventObserver {
            if (it is Result.Success && it.data?.success == true) {
                it.data.let { result ->
                    // Save guest session id
                    PreferenceUtil.writeGuestToken(requireActivity(), result.guestSessionId)
                    findNavController().navigate(
                        AuthenticationFragmentDirections.actionAuthenticationFragmentToHomeFragment()
                    )
                }
            }
        })

        return binding.root
    }

}