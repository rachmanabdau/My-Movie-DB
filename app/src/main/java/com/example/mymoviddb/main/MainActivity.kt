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
        setSupportActionBar(binding.mainToolbar.toolbar)
        setupNavController(binding)
        observeUserAvatar()
        observeLogoutResult()
    }

    override fun onResume() {
        super.onResume()
        if (isUserLogin()) mainViewModel.getUserDetail(userPreference.readUserSession())
    }

    private fun setupNavController(binding: ActivityMainBinding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.mainHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        setupToolbar(navController)
    }

    private fun setupToolbar(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentDestination = setOf(R.id.detailGraph)
            setSupportActionBar(binding.mainToolbar.toolbar)
            setupLayout(navController)
            binding.mainToolbar.toolbar.isVisible =
                fragmentDestination.contains(destination.id).not()
        }
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
                it.data?.let { detail ->
                    usernameHeader.text = detail.username
                    userPreference.writeAccountId(detail.id)
                    loadUserAvatar(userAvatar, detail.avatar.tmdb, detail.username)
                }
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
                launchLogoutConfirmation()
            }

            // to maintain navigation component still work
            NavigationUI.onNavDestinationSelected(it, navController)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

    }

    private fun launchLogoutConfirmation() {
        val alertDialog: AlertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton("Yes") { dialog, _ ->
                    mainViewModel.logout(userPreference.readUserSession())
                    dialog.dismiss()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
            }
            builder.setTitle(getString(R.string.log_out))
                .setMessage(getString(R.string.log_out_confirmation))
            // Create the AlertDialog
            builder.create()
        }

        alertDialog.show()
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