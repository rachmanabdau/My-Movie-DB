package com.example.mymoviddb.authentication.guest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.utils.EventObserver
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.UserPreference
import com.example.mymoviddb.databinding.FragmentAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    private val authenticationViewModel by viewModels<AuthenticationViewModel>()

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                userPreference.setAuthState(LoginState.AS_GUEST)
            }
        })

        return binding.root
    }
}
