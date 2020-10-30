package com.example.mymoviddb.authentication.guest

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.mymoviddb.R
import com.example.mymoviddb.authentication.di.ServiceModule
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.launchFragmentInHiltContainer
import com.example.mymoviddb.sharedData.FakeRemoteServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Singleton

@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(ServiceModule::class)
class AuthenticationFragmentTest {

    @get:Rule
    var hiltRulehiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRulehiltRule.inject()
    }

    @Module
    @InstallIn(ApplicationComponent::class)
    object TestModule {
        @Provides
        @Singleton
        fun ProvideAccess(): NetworkService = FakeRemoteServer()
    }

    @Test
    fun loginAsGuestLoginValidApiKey() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AuthenticationFragment>(Bundle(), R.style.AppTheme) {
            // Do not use it.requireView() because it won't work. instead use it.view!!
            Navigation.setViewNavController(this.view!!, navController)
        }

        onView(withId(R.id.login_as_guest)).check(matches(isDisplayed()))
        onView(withId(R.id.tmdb_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.login_as_guest)).perform(click())

        verify(navController).navigate(
            AuthenticationFragmentDirections.actionAuthenticationFragmentToHomeFragment()
        )
    }
}