package com.example.mymoviddb.core

import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar

open class FragmentWithDefaultToolbar: Fragment() {

    open fun setupDefaultToolbar(toolbar: MaterialToolbar, navController: NavController){
        setHasOptionsMenu(true)
        toolbar.setupWithNavController(navController)
    }
}