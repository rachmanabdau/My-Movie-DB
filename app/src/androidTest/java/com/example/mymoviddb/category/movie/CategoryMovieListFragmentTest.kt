package com.example.mymoviddb.category.movie

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
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
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@HiltAndroidTest
@UninstallModules(ServiceModule::class)
@RunWith(AndroidJUnit4::class)
@MediumTest
class CategoryMovieListFragmentTest {
    @get:Rule
    var hiltRulehiltRule = HiltAndroidRule(this)

    @Module
    @InstallIn(ApplicationComponent::class)
    object TestHomeModule {
        @Provides
        fun provideFakeServer(): NetworkService = FakeRemoteServer()
    }

    /**
     * Click on load more popular movies navigation method to category movies
     * with popular movie id executed
     */
    @Test
    fun testHome_navigateToCategoryPopularMovie() {
        // GIVEN a mock  NavController class
        val navController = Mockito.mock(NavController::class.java)

        // Launch Home Fragment
        val bundle =
            CategoryMovieListFragmentArgs(R.string.popular_movie_list_contentDesc).toBundle()
        launchFragmentInHiltContainer<CategoryMovieListFragment>(bundle, R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }

        // check if toolbar is equal to "popular movie list
        onView(withContentDescription(R.string.popular_movie_list_contentDesc)).check(
            matches(
                isDisplayed()
            )
        )
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