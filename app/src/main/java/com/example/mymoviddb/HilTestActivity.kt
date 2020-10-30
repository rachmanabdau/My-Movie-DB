package com.example.mymoviddb

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Register this activity in AndroidManifest.xml for instrumented testing with Hil.
 * see [FragmentHiltContainer.kt] for detail
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()