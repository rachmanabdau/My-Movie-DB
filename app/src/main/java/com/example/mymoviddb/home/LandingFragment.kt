package com.example.mymoviddb.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.UserPreference
import com.example.mymoviddb.databinding.FragmentLandingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingFragment : Fragment() {

    private lateinit var binding: FragmentLandingBinding

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.loginWithAccount.setOnClickListener {
            findNavController().navigate(
                LandingFragmentDirections.actionLandingFragmentToLoginFragment()
            )
        }

        binding.loginAsGuest.setOnClickListener {
            findNavController().navigate(
                LandingFragmentDirections.actionLandingFragmentToHomeFragment()
            )
            userPreference.setAuthState(LoginState.AS_GUEST)
        }

        return binding.root
    }
}
