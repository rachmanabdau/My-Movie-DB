package com.example.mymoviddb.authentication.guest

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
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
import com.example.mymoviddb.authentication.IAuthenticationAccess
import com.example.mymoviddb.authentication.ServiceLocatorAuthentication
import com.example.mymoviddb.sharedData.FakeAuthenticationAccess
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class AuthenticationFragmentTest {

    private lateinit var authAccess: IAuthenticationAccess

    @Before
    fun setup() {
        authAccess = FakeAuthenticationAccess()
        ServiceLocatorAuthentication.authenticationAccess = authAccess
    }

    @After
    fun reset() {
        ServiceLocatorAuthentication.resetAuthenticationAccess()
    }

    @Test
    fun loginAsGuestLoginValidApiKey() {
        val scenario = launchFragmentInContainer<AuthenticationFragment>(Bundle(), R.style.AppTheme)

        val navController = mock(NavController::class.java)
        scenario.onFragment {
            // Do not use it.requireView() because it won't work. instead use it.view!!
            Navigation.setViewNavController(it.view!!, navController)
        }

        onView(withId(R.id.login_as_guest)).check(matches(isDisplayed()))
        onView(withId(R.id.tmdb_icon)).check(matches(isDisplayed()))
        onView(withId(R.id.login_as_guest)).perform(click())

        verify(navController).navigate(
            AuthenticationFragmentDirections.actionAuthenticationFragmentToHomeFragment()
        )
    }
}