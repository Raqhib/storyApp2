package id.co.mondo.storyapp.ui.auth.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import id.co.mondo.storyapp.R
import id.co.mondo.storyapp.ui.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest{

    @get:Rule
    val activity = ActivityScenarioRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @org.junit.Test
    fun loginIsSuccessAndLogoutIsSuccess() {

        onView(withId(R.id.emailEditText_login)).perform(
            typeText("acoganteng@gmail.com"), closeSoftKeyboard()
        )
        onView(withId(R.id.passwordEditText)).perform(
            typeText("qwertyuiop"), closeSoftKeyboard()
        )

        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.main_real))
            .check(matches(isDisplayed()))

        onView(withId(R.id.logout_btn)).perform(click())

        onView(withId(R.id.emailEditText_login))
            .check(matches(isDisplayed()))
    }

}