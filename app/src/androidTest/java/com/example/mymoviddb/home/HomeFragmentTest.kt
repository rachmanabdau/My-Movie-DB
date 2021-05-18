package com.example.mymoviddb.home

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.mymoviddb.R
import com.example.mymoviddb.ScrollToWithNestedScrollView
import com.example.mymoviddb.category.ShowCategoryIndex
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.di.ServiceModule
import com.example.mymoviddb.launchFragmentInHiltContainer
import com.example.mymoviddb.sharedData.FakeRemoteServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
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
    @InstallIn(SingletonComponent::class)
    object TestHomeModule {
        @Provides
        fun provideHomeAccess(): NetworkService = FakeRemoteServer()
    }

    /**
     * Click on load more popular movies navigation method to category movies
     * with popular movie id executed
     */
    @Test
    fun testHome_navigateToCategoryPopularMovie() {
        // GIVEN a mock  NavController class
        val navController = mock(NavController::class.java)

        // Launch Home Fragment
        launchFragmentInHiltContainer<HomeFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // check if popular movie text view is displayed and has valid text
        onView(withId(R.id.popular_movie_txtv)).check(matches(isDisplayed()))
        onView(withId(R.id.popular_movie_txtv)).check(matches(withText(R.string.popular_movie)))
        // check if popular movie container adn recycelerview are displayed
        onView(withId(R.id.popular_movie_container)).check(matches(isDisplayed()))
        onView(withId(R.id.popular_movie_rv)).check(matches(isDisplayed()))
        // click last item / 'load more' in popular movies list
        onView(withId(R.id.popular_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )

        // check if we clicked on load more popular movie navigation to category movies is clicked
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                R.string.popular_movie_list_contentDesc, ShowCategoryIndex.POPULAR_MOVIES
            )
        )
    }

    /**
     * Click on load more now playing movies navigation method to category movies
     * with now playing movie id executed
     */
    @Test
    fun testHome_navigateToCategoryNowPlayingMovie() {
        // GIVEN a mock  NavController class
        val navController = mock(NavController::class.java)

        // Launch Home Fragment
        launchFragmentInHiltContainer<HomeFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // check if now playing movie text view is displayed and has valid text
        onView(withId(R.id.now_playing_movie_txtv)).check(matches(isDisplayed()))
        // check if now playing movie container and recycelerview are displayed
        onView(withId(R.id.now_playing_movie_container)).check(matches(isDisplayed()))
        onView(withId(R.id.now_playing_movie_rv)).check(matches(isDisplayed()))
        // click last item / 'load more' in now playing movies list
        onView(withId(R.id.now_playing_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )

        // check if we clicked on load more now playing movie navigation to category movies is clicked
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                R.string.now_playing_movie_list_contentDesc, ShowCategoryIndex.NOW_PLAYING_MOVIES
            )
        )
    }

    /**
     * Click on load more popular tv show navigation method to category tv show
     * with tv show movie id executed
     */
    @Test
    fun testHome_navigateToCategoryPopularTVShow() {
        // GIVEN a mock  NavController class
        val navController = mock(NavController::class.java)

        // Launch Home Fragment
        launchFragmentInHiltContainer<HomeFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // check if popular tv show text view is displayed and has valid text
        onView(withId(R.id.popular_tv_txtv)).check(matches(isDisplayed()))
        onView(withId(R.id.popular_tv_txtv)).check(matches(withText(R.string.popular_tv_show)))
        // check if popular tv show container adn recycelerview are displayed
        onView(withId(R.id.popular_tv_container)).check(matches(isDisplayed()))
        onView(withId(R.id.popular_tv_rv)).check(matches(isDisplayed()))
        // click last item / 'load more' in popular tv show list
        onView(withId(R.id.popular_tv_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )

        // check if we clicked on load more popular tv show navigation to category tv show is clicked
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                R.string.popular_tv_show_list_contentDesc, ShowCategoryIndex.POPULAR_TV_SHOWS
            )
        )
    }

    /**
     * Click on load more on air TV Show navigation method to category TV Show
     * with on air TV Show id executed
     */
    @Test
    fun testHome_navigateToCategoryOnAirTVShow() {
        // GIVEN a mock  NavController class
        val navController = mock(NavController::class.java)

        // Launch Home Fragment
        launchFragmentInHiltContainer<HomeFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        onView(withId(R.id.on_air_tv_container)).perform(ScrollToWithNestedScrollView())

        Thread.sleep(2000)

        // check if on air tv show text view is displayed and has valid text
        onView(withId(R.id.on_air_tv_txtv)).check(matches(isDisplayed()))
        // check if on air tv show container and recycelerview are displayed
        onView(withId(R.id.on_air_tv_container)).check(matches(isDisplayed()))
        onView(withId(R.id.on_air_popular_tv_rv)).check(matches(isDisplayed()))
        // click last item / 'load more' in on air tv show list
        onView(withId(R.id.on_air_popular_tv_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )

        // check if we clicked on load more on air tv show navigation to category tv show is clicked
        verify(navController).navigate(
            HomeFragmentDirections.actionHomeFragmentToCategoryMovieListFragment(
                R.string.now_airing_tv_show_list_contentDesc, ShowCategoryIndex.ON_AIR_TV_SHOWS
            )
        )
    }

}
