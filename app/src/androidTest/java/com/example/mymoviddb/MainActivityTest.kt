package com.example.mymoviddb

import android.app.Activity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.mymoviddb.core.utils.DataBindingIdlingResource
import com.example.mymoviddb.core.utils.EspressoIdlingResource
import com.example.mymoviddb.core.utils.monitorActivity
import com.example.mymoviddb.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

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

    @Test
    fun testGlobalNavigationAndView_usingBackButton() = runBlocking {
        // Start up My Movie DB
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //verify that authentication fragment is displayed and click button login as a guest
        onView(withText(R.string.login_as_guest)).check(matches(isDisplayed()))
            .perform(click())

        // verify that loading movies and tv shows list is sucessfully loaded
        // verify that error message popular movies message is not displayed
        onView(withId(R.id.error_popular_movies_message)).check(matches(not(isDisplayed())))
        // verify that error message now playing movies message is not displayed
        onView(withId(R.id.error_now_playing_movies_message)).check(matches(not(isDisplayed())))
        // verify that error message popular tv shows message is not displayed
        onView(withId(R.id.error_popular_tv_message)).check(matches(not(isDisplayed())))
        // verify that error message on air tv shows message is not displayed
        onView(withId(R.id.error_on_air_tv_message)).check(matches(not(isDisplayed())))

        // click on load more popular movies
        onView(withId(R.id.popular_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more popular movies category popular movie list ist displayed
        onView(withText(R.string.popular_movie_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press back to home fragment
        Espresso.pressBack()
        // click on load more popular movies
        onView(withId(R.id.now_playing_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more now playing movies category popular movi list ist displayed
        onView(withText(R.string.now_playing_movie_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press back to home fragment
        Espresso.pressBack()


        // click on load more popular movies
        onView(withId(R.id.popular_tv_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more popular tv shows category popular tv shows list ist displayed
        onView(withText(R.string.popular_tv_show_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press back to home fragment
        Espresso.pressBack()

        // scroll to on air tv shows recycler view
        onView(withId(R.id.on_air_popular_tv_rv)).perform(ScrollToWithNestedScrollView())
        // click on load more popular movies
        onView(withId(R.id.on_air_popular_tv_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more on air tv shows category popular tv shows list ist displayed
        onView(withText(R.string.now_airing_tv_show_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press back to home fragment
        Espresso.pressBack()

        // press back to login fragment
        Espresso.pressBack()
        // verify that tool bar name is " My Movie DB"
        onView(withId(R.id.login)).check(matches(isDisplayed()))

        // Make sure the activity is closed before resetting the db.
        activityScenario.close()
    }

    @Test
    fun testGlobalNavigationAndView_usingUpButton() = runBlocking {
        // Start up My Movie DB
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(activityScenario)

        //verify that authentication fragment is displayed and click button login as a guest
        onView(withText(R.string.login_as_guest)).check(matches(isDisplayed()))
            .perform(click())

        // verify that loading movies and tv shows list is sucessfully loaded
        // verify that error message popular movies message is not displayed
        onView(withId(R.id.error_popular_movies_message)).check(matches(not(isDisplayed())))
        // verify that error message now playing movies message is not displayed
        onView(withId(R.id.error_now_playing_movies_message)).check(matches(not(isDisplayed())))
        // verify that error message popular tv shows message is not displayed
        onView(withId(R.id.error_popular_tv_message)).check(matches(not(isDisplayed())))
        // verify that error message on air tv shows message is not displayed
        onView(withId(R.id.error_on_air_tv_message)).check(matches(not(isDisplayed())))

        // click on load more popular movies
        onView(withId(R.id.popular_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more popular movies category popular movie list ist displayed
        onView(withText(R.string.popular_movie_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press up button to home fragment
        onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(
            click()
        )

        // click on load more popular movies
        onView(withId(R.id.now_playing_movie_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more now playing movies category popular movi list ist displayed
        onView(withText(R.string.now_playing_movie_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press up button to home fragment
        onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(
            click()
        )


        // click on load more popular movies
        onView(withId(R.id.popular_tv_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more popular tv shows category popular tv shows list ist displayed
        onView(withText(R.string.popular_tv_show_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press up button to home fragment
        onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(
            click()
        )

        // scroll to on air tv shows recycler view
        onView(withId(R.id.on_air_popular_tv_rv)).perform(ScrollToWithNestedScrollView())
        // click on load more popular movies
        onView(withId(R.id.on_air_popular_tv_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                20, click()
            )
        )
        // Verify that when user click on load more on air tv shows category popular tv shows list ist displayed
        onView(withText(R.string.now_airing_tv_show_list_contentDesc)).check(matches(isDisplayed()))
        // Verify that error message does not displayed (load data is succeed)
        onView(withId(R.id.error_layout)).check(matches(not(isDisplayed())))
        // press up button to home fragment
        onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(
            click()
        )

        // press up button to login fragment
        Espresso.pressBack()
        /*onView(withContentDescription(activityScenario.getToolbarNavigationContentDescription())).perform(
            click()
        )*/
        // verify that tool bar name is " My Movie DB"
        onView(withId(R.id.login)).check(matches(isDisplayed()))


        // Make sure the activity is closed before resetting the db.
        activityScenario.close()
    }

}

fun <T : Activity> ActivityScenario<T>.getToolbarNavigationContentDescription()
        : String {
    var description = ""
    onActivity {
        description =
            it.findViewById<Toolbar>(R.id.toolbar).navigationContentDescription as String
    }
    return description
}
