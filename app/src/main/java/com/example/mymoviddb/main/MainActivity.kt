package com.example.mymoviddb.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
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
import com.example.mymoviddb.model.UserDetail
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.UserPreference
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.mainToolbar.toolbar)
        setupStartDestination(binding)
        observeUserAvatar()
        observeLogoutResult()
    }

    override fun onResume() {
        super.onResume()
        if (isUserLogin()) mainViewModel.getUserDetail(userPreference.readUserSession())
    }

    private fun setupStartDestination(binding: ActivityMainBinding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        val navigationInflater = navHostFragment.navController.navInflater
        val graph = navigationInflater.inflate(R.navigation.main_navigation)
        graph.startDestination =
            if (isUserLogin()) R.id.homeFragment else R.id.authenticationFragment
        navController.graph = graph

        setupLayout(navController)
    }

    private fun setupLayout(navController: NavController) {
        if (isUserLogin()) {
            setupDrawerMenu(navController)
        } else {
            setupToolbarOnly(navController)
        }
    }

    private fun isUserLogin(): Boolean {
        return userPreference.getAuthState() == LoginState.AS_USER.stateId
    }

    private fun observeUserAvatar() {
        mainViewModel.userDetail.observe(this) {
            val headerItem = binding.navView.getHeaderView(0)
            val userAvatar =
                (headerItem.findViewById<CircleImageView>(R.id.user_avatar_header))
            val usernameHeader =
                (headerItem.findViewById<TextView>(R.id.username_header))
            if (it is Result.Success && it.data != null) {
                usernameHeader.text = it.data.username
                userPreference.writeAccountId(it.data.id)
                loadUserAvatar(userAvatar, it.data.avatar.tmdb, it.data.username)
            }
        }
    }

    private fun loadUserAvatar(
        userIMageView: ImageView,
        userTmdb: UserDetail.Avatar.Tmdb?,
        username: String
    ) {
        if (userTmdb != null) {
            Glide.with(this)
                .load(BuildConfig.LOAD_POSTER_BASE_URL + userTmdb.avatarPath)
                .into(userIMageView)
        } else {
            Glide.with(this)
                .load("https://eu.ui-avatars.com/api/?background=random&name=${username}")
                .into(userIMageView)
        }
    }

    private fun observeLogoutResult() {
        mainViewModel.logoutResult.observe(this) {
            if (it is Result.Success && it.data?.success == true) {
                userPreference.logout()
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            if (it is Result.Error) {
                Snackbar.make(
                    binding.root,
                    it.exception.localizedMessage ?: "Unknown error occurred",
                    Snackbar.LENGTH_SHORT
                ).show()
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

        // listener click for logout menu. it has to be placed right after NavigationUI.setupWithNavController
        binding.navView.setNavigationItemSelectedListener {
            if (it.itemId == R.id.logout_drawer_menu) {
                mainViewModel.logout(userPreference.readUserSession())
            }

            // to maintain navigation component still work
            NavigationUI.onNavDestinationSelected(it, navController)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

    }

    private fun setupToolbarOnly(navController: NavController) {
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val appbarConfig = AppBarConfiguration(navController.graph)
        binding.mainToolbar.toolbar.setupWithNavController(navController, appbarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
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