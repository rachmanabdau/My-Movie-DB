package com.example.mymoviddb.main

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.mymoviddb.BuildConfig
import com.example.mymoviddb.R
import com.example.mymoviddb.databinding.ActivityMainBinding
import com.example.mymoviddb.model.Result
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val navigationInflater = navHostFragment.navController.navInflater
        val graph = navigationInflater.inflate(R.navigation.main_navigation)
        graph.startDestination =
            if (PreferenceUtil.getAuthState(this) == LoginState.AS_USER.ordinal) {
                R.id.homeFragment
            } else {
                R.id.authenticationFragment
            }

        navController.graph = graph

        if (PreferenceUtil.getAuthState(this) == LoginState.AS_USER.ordinal) {
            setupDrawerMenu(navController)
            onserveUserAvatar(PreferenceUtil.readUserSession(this))
        } else {
            setupToolbarOnly(navController)
        }
    }

    private fun onserveUserAvatar(readUserSession: String) {
        mainViewModel.getUserDetail(readUserSession)
        mainViewModel.userDetail.observe(this) {
            val headerItem = binding.navView.getHeaderView(0)
            val profileAvaterHeader =
                (headerItem.findViewById<CircleImageView>(R.id.user_avatar_header))
            val profileUsernameHeader =
                (headerItem.findViewById<TextView>(R.id.username_header))
            if (it is Result.Success) {
                profileUsernameHeader.text = it.data?.username
                if (it.data?.avatar?.tmdb != null) {
                    Glide.with(this)
                        .load(BuildConfig.LOAD_POSTER_BASE_URL + it.data.avatar.tmdb.avatarPath)
                        .into(profileAvaterHeader)
                    Snackbar.make(binding.root, "available avatar executed", Snackbar.LENGTH_SHORT)
                        .show()
                } else {
                    Glide.with(this)
                        .load("https://eu.ui-avatars.com/api/?background=random&name=${profileUsernameHeader.text}")
                        .into(profileAvaterHeader)
                    Snackbar.make(
                        binding.root,
                        "unavailable avatar executed",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun setupDrawerMenu(navController: NavController) {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        val appbarConfig = AppBarConfiguration(navController.graph, binding.drawerLayout)
        NavigationUI.setupWithNavController(
            binding.mainToolbar.toolbar,
            navController,
            appbarConfig
        )
        binding.navView.setupWithNavController(navController)
    }

    private fun setupToolbarOnly(navController: NavController) {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val appbarConfig = AppBarConfiguration(navController.graph)
        binding.mainToolbar.toolbar.setupWithNavController(navController, appbarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        //without drawer menu or appbar configuration
        return Navigation.findNavController(this, R.id.main_host_fragment).navigateUp()
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}