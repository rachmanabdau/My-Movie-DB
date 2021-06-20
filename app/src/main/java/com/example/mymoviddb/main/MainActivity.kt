package com.example.mymoviddb.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.mymoviddb.R
import com.example.mymoviddb.core.BuildConfig
import com.example.mymoviddb.core.model.Result
import com.example.mymoviddb.core.model.UserDetail
import com.example.mymoviddb.core.utils.preference.LoginState
import com.example.mymoviddb.core.utils.preference.UserPreference
import com.example.mymoviddb.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isUserLogin()) userPreference.setAuthState(LoginState.NOT_LOGIN)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    private fun isUserLogin(): Boolean {
        return userPreference.getAuthState() == LoginState.AS_USER.stateId
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.main_host_fragment).navigateUp()
                || super.onSupportNavigateUp()
    }
}