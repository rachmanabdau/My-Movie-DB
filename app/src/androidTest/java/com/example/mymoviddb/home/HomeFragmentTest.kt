package com.example.mymoviddb.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.mymoviddb.R
import com.example.mymoviddb.datasource.remote.NetworkService
import com.example.mymoviddb.di.ServiceModule
import com.example.mymoviddb.launchFragmentInHiltContainer
import com.example.mymoviddb.sharedData.FakeRemoteServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@HiltAndroidTest
@UninstallModules(ServiceModule::class)
@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest {

    @get:Rule
    var hiltRulehiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(ApplicationComponent::class)
    object TestHomeModule {
        @Provides
        fun provideHomeAccess(): NetworkService = FakeRemoteServer()
    }

    @Test
    fun testHome_navigateToCategoryPopularMovie() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        onView(withId(R.id.popular_movie_txtv)).perform().check(matches(isDisplayed()))
        onView(withId(R.id.popular_movie_container)).perform().check(matches(isDisplayed()))
        onView(withId(R.id.popular_movie_rv)).check(matches(isDisplayed()))
        onView(withId(R.id.popular_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                19, click()
            )
        )

        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                1,
                R.string.popular_movie_list_contentDesc
            )
        )
    }

}
