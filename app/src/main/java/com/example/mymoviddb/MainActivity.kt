package com.example.mymoviddb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.mymoviddb.databinding.ActivityMainBinding
import com.example.mymoviddb.utils.LoginState
import com.example.mymoviddb.utils.PreferenceUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
        val appbarConfig = AppBarConfiguration(navController.graph)
        binding.mainToolbar.toolbar.setupWithNavController(navController, appbarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        //without drawer menu or appbar configuration
        return Navigation.findNavController(this, R.id.main_host_fragment).navigateUp()
                || super.onSupportNavigateUp()
    }
}