package com.example.mymoviddb.category.movie

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.mymoviddb.R
import com.example.mymoviddb.category.CategoryShowListFragment
import com.example.mymoviddb.category.MovieDataSource
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
    @InstallIn(ApplicationComponent::class)
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

        // Launch Home Fragment
        MovieDataSource.MOVIE_CATEGORY_ID = MovieDataSource.POPULAR_MOVIE_ID
        val bundle =
            CategoryMovieListFragmentArgs(R.string.popular_movie_list_contentDesc).toBundle()
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

        // Launch Home Fragment
        MovieDataSource.MOVIE_CATEGORY_ID = MovieDataSource.POPULAR_MOVIE_ID
        val bundle =
            CategoryMovieListFragmentArgs(R.string.now_playing_movie_list_contentDesc).toBundle()
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
}