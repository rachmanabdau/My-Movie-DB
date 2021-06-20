package com.example.mymoviddb.feature.category

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.mymoviddb.core.mock.FakeRemoteServer
import com.example.mymoviddb.core.ShowCategoryIndex
import com.example.mymoviddb.core.datasource.remote.NetworkService
import com.example.mymoviddb.core.di.ServiceModule
import com.example.mymoviddb.core.utils.test.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.hamcrest.core.IsNot
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@HiltAndroidTest
@UninstallModules(ServiceModule::class)
@RunWith(AndroidJUnit4::class)
@MediumTest
class CategoryShowListFragmentTest {
    @get:Rule
    var hiltRulehiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(SingletonComponent::class)
    object TestHomeModule {
        @Provides
        fun provideFakeServer(): NetworkService = FakeRemoteServer()
    }

    /**
     * Check category movies is loading the right data (Popular movies)
     */
    @Test
    fun test_PopularMovieCategoryList() {
        // GIVEN a mock  NavController class
        val navController = Mockito.mock(NavController::class.java)

        // Launch category show fragment with popular movies in the list
        val bundle =
            CategoryShowListFragmentArgs(
                R.string.popular_movie_list_contentDesc,
                ShowCategoryIndex.POPULAR_MOVIES
            ).toBundle()

        launchFragmentInHiltContainer<CategoryShowListFragment>(bundle, R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // chack if recyclerview is showing data
        onView(withId(R.id.show_rv)).check(matches(isCompletelyDisplayed()))
        // check that the list os not showing error message
        onView(withId(R.id.error_layout)).check(matches(not(isCompletelyDisplayed())))
        // click last item / 'load more' in popular movies list
        onView(withId(R.id.show_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    /**
     * Check category movies is loading the right data (Now Playing movies)
     */
    @Test
    fun test_NowPlayingMovies() {
        // GIVEN a mock  NavController class
        val navController = Mockito.mock(NavController::class.java)

        // Launch category show fragment with now playing movies in the list
        val bundle =
            CategoryShowListFragmentArgs(
                R.string.now_playing_movie_list_contentDesc,
                ShowCategoryIndex.NOW_PLAYING_MOVIES
            ).toBundle()
        launchFragmentInHiltContainer<CategoryShowListFragment>(bundle, R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // chack if recyclerview is showing data
        onView(withId(R.id.show_rv)).check(matches(isCompletelyDisplayed()))
        // check that the list os not showing error message
        onView(withId(R.id.error_layout)).check(matches(not(isCompletelyDisplayed())))
        // click last item / 'load more' in popular movies list
        onView(withId(R.id.show_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    /**
     * Check category movies is loading the right data (Popular TV Shows)
     */
    @Test
    fun test_PoplarTVShows() {
        // GIVEN a mock  NavController class
        val navController = Mockito.mock(NavController::class.java)

        // Launch Home Fragment
        val bundle =
            CategoryShowListFragmentArgs(
                R.string.now_airing_tv_show_list_contentDesc,
                ShowCategoryIndex.POPULAR_TV_SHOWS
            ).toBundle()
        launchFragmentInHiltContainer<CategoryShowListFragment>(bundle, R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // chack if recyclerview is showing data
        Espresso.onView(ViewMatchers.withId(R.id.show_rv))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        // check that the list os not showing error message
        Espresso.onView(ViewMatchers.withId(R.id.error_layout))
            .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isCompletelyDisplayed())))
        // click last item / 'load more' in popular movies list
        Espresso.onView(ViewMatchers.withId(R.id.show_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }

    /**
     * Check category movies is loading the right data (Popular TV Shows)
     */
    @Test
    fun test_NowAiringTVShows() {
        // GIVEN a mock  NavController class
        val navController = Mockito.mock(NavController::class.java)

        // Launch Home Fragment
        val bundle =
            CategoryShowListFragmentArgs(
                R.string.now_airing_tv_show_list_contentDesc,
                ShowCategoryIndex.ON_AIR_TV_SHOWS
            ).toBundle()
        launchFragmentInHiltContainer<CategoryShowListFragment>(bundle, R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // chack if recyclerview is showing data
        Espresso.onView(ViewMatchers.withId(R.id.show_rv))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        // check that the list os not showing error message
        Espresso.onView(ViewMatchers.withId(R.id.error_layout))
            .check(ViewAssertions.matches(IsNot.not(ViewMatchers.isCompletelyDisplayed())))
        // click last item / 'load more' in popular movies list
        Espresso.onView(ViewMatchers.withId(R.id.show_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )
    }
}