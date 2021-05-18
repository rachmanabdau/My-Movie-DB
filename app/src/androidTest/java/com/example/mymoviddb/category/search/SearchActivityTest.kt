package com.example.mymoviddb.category.search

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.mymoviddb.R
import com.example.mymoviddb.core.utils.DataBindingIdlingResource
import com.example.mymoviddb.core.utils.EspressoIdlingResource
import com.example.mymoviddb.core.utils.monitorActivity
import com.example.mymoviddb.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class SearchActivityTest {

    @get:Rule
    var hiltRulehiltRule = HiltAndroidRule(this)

    // An idling resource that waits for Data Binding to have no pending bindings.
    private val dataBindingIdlingResource = DataBindingIdlingResource()

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().register(dataBindingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        IdlingRegistry.getInstance().unregister(dataBindingIdlingResource)
    }


    /**
     * Check category movies is loading the right data (Popular movies)
     */
    @Test
    fun test_searchMovieActivity() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //verify that authentication fragment is displayed and click button login as a guest
        onView(withText(R.string.login_as_guest))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.chooser_dialog_hint)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.movie_chooser_container)).perform(click())

        // inpu title keyword with blank string
        // chack if recyclerview is not showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText(" "))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // chack if snackbar hint is poped out
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.invalid_search_title)))*/

        // inpu title keyword with empty string
        // chack if recyclerview is not showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText(""))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // chack if snackbar hint is poped out
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.invalid_search_title)))*/

        // input title keyword with movie that does not exist
        // chack if recyclerview is not showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText("sfasdf"))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(isCompletelyDisplayed()))

        // input title with valid keyboard
        // chack if recyclerview is showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText("it"))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // scroll to position 20
        onView(withId(R.id.search_shows_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )

        // chack if recyclerview is showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText("frozen"))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // scroll to position 20
        onView(withId(R.id.search_shows_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )

        // press back to main activity with home fragmnet
        Espresso.pressBack()
        onView(withText(R.string.home_label)).check(matches(isDisplayed()))
        onView(withText(R.string.popular_movie)).check(matches(isDisplayed()))
        onView(withText(R.string.now_playing_movies)).check(matches(isDisplayed()))
        Espresso.pressBack()
        // press back to authentocation fragment
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))

        activityScenario.moveToState(Lifecycle.State.DESTROYED)
        activityScenario.close()
    }


    /**
     * Check category movies is loading the right data (Popular movies)
     */
    @Test
    fun test_searchTVShowsActivity() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //verify that authentication fragment is displayed and click button login as a guest
        onView(withText(R.string.login_as_guest))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.action_search)).perform(click())
        onView(withId(R.id.chooser_dialog_hint)).check(matches(isCompletelyDisplayed()))
        onView(withId(R.id.tv_chooser_container)).perform(click())

        // inpu title keyword with blank string
        // chack if recyclerview is not showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText(" "))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // chack if snackbar hint is poped out
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.invalid_search_title)))*/

        // inpu title keyword with empty string
        // chack if recyclerview is not showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText(""))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // chack if snackbar hint is poped out
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.invalid_search_title)))*/

        // input title keyword with movie that does not exist
        // chack if recyclerview is not showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText("sfasdf"))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(isCompletelyDisplayed()))

        // input title with valid keyboard
        // chack if recyclerview is showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText("the"))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // scroll to position 20
        onView(withId(R.id.search_shows_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )

        // chack if recyclerview is showing data
        onView(withHint(R.string.search_hint))
            .perform(replaceText("is"))
            .perform(pressImeActionButton())
        // check that the list os not showing error message
        onView(withId(R.id.error_layout))
            .check(matches(not(isCompletelyDisplayed())))
        // scroll to position 20
        onView(withId(R.id.search_shows_rv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                20
            )
        )

        // close soft keyboard
        Espresso.pressBack()
        // press back to main activity with home fragmnet
        Espresso.pressBack()
        onView(withText(R.string.home_label)).check(matches(isDisplayed()))
        onView(withText(R.string.popular_movie)).check(matches(isDisplayed()))
        onView(withText(R.string.now_playing_movies)).check(matches(isDisplayed()))
        Espresso.pressBack()
        // press back to authentocation fragment
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))

        activityScenario.close()
    }

}