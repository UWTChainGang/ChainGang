
/*
 * LoginCredentialsFragmentTest.java
 *
 * ChainGang <A hub for managing your complex processes>
 * Class to do the automated(instrumental) testing for the following:
 *      1. User authentication with correct credentials;
 *      2. Deny an access by login with the wrong email address;
 *      3. Deny am Access by login with the wrong user password.
 *
 * Author: Michael Quandt
 * Author: James E Johnston
 * Author: Denis Yakovlev
 * Version: 23 May 2017
 */


package edu.tacoma.uw.css.uwtchaingang.chaingang;

import android.support.annotation.IdRes;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * ChainGang <A hub for managing your complex processes>
 * Class to do the automated(instrumental) testing for the following:
 *      1. User authentication with correct credentials;
 *      2. Deny an access by login with the wrong email address;
 *      3. Deny am Access by login with the wrong user password.
 *
 * @author Michael Quandt
 * @author James E Johnston
 * @author Denis Yakovlev
 * @version 23 May 2017
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginCredentialsFragmentTest {

    /**
     * A JUnit {@link Rule @Rule} to launch your activity under test.
     * Rules are interceptors which are executed for each test method and will run before
     * any of your setup code in the {@link @Before} method.
     * <p>
     * {@link ActivityTestRule} will create and launch of the activity for you and also expose
     * the activity under test. To get a reference to the activity you can use
     * the {@link ActivityTestRule#getActivity()} method.
     */
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);


    /**
     * Before the main test of the LoginCredentialsFragment need to launch LoginFragment first
     */
    @Before
    public void testLaunchLoginFragment() {

        onView(withId(R.id.login_btn))
                .perform(click());

    }


    /**
     * Test for successful login
     */
    @Test
    public void TestOpenChainActivity() {

        onView(withId(R.id.email_credentials)).perform(typeText("test@test.com"));
        onView(withId(R.id.password_credentials)).perform(typeText("Test23@#"));
        onView(ViewMatchers.withId(R.id.login_btn_credentials)).perform(click());
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));


        onView(ViewMatchers.withId(R.id.action_logout)).perform(click());

    }


    /**
     * Test for unsuccessful login with the wrong password
     */
    @Test
    public void testLoginFragmentInvalidPassword() {

        onView(withId(R.id.email_credentials)).perform(typeText("test@test.com"));
        onView(withId(R.id.password_credentials)).perform(typeText("ttttt"));
        onView(ViewMatchers.withId(R.id.login_btn_credentials)).perform(click());

        onView(withText("Enter valid password (at least 8 characters)"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));

    }


    /**
     * Test for unsuccessful login with the wrong email address
     */
    @Test
    public void testLoginFragmentInvalidEmail() {

        onView(withId(R.id.email_credentials)).perform(typeText("testtest.com"));
        onView(withId(R.id.password_credentials)).perform(typeText("Test23@#"));
        onView(ViewMatchers.withId(R.id.login_btn_credentials)).perform(click());

        onView(withText("Enter valid email address"))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity()
                        .getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));

    }

}
