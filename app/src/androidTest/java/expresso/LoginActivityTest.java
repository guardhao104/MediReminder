package expresso;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.medireminder.R;
import com.example.medireminder.ui.login.LoginActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
    private  String[] names = {"", "a", "123123", "tester5@gmail.com"};
    private  String[] pwds = {"", "a", "123123", "tester5"};

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<LoginActivity>(LoginActivity.class);

    @Before
    public void init() {
        Log.e("TAG", "init: ");
    }

    // test all component are active in view
    @Test
    public void  test_isActivityInView() {
        onView(withId(R.id.banner)).check(matches(isDisplayed()));
        onView(withId(R.id.bannerDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.signIn)).check(matches(isDisplayed()));
        onView(withId(R.id.register)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    // test the title display is correct
    @Test
    public void test_isTitleTextDisplayed() {
        onView(withId(R.id.banner)).check(matches(withText("MediReminder")));
    }

    // enter different type of wrong passwords to check if load the landing page
    @Test
    public void test_incorrectLogin() {

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(names[1]), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(pwds[1]), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(names[1]), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(pwds[1]), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(names[2]), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(pwds[2]), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

//        onView(withText("Authentication failed."))
//                .inRoot(new ToastMatcher())
//                .check(matches(isDisplayed()));
    }

    // nav to landing page
    @Test
    public void test_navLandingPageActivity() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(names[3]), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(pwds[3]), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
//        onView(withId(R.id.landing)).check(matches(isDisplayed()));
    }

    // nav to landing page and back to login page
    @Test
    public void test_LandingPageActivityBackLogin() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(names[3]), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(pwds[3]), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(withId(R.id.logOutButton)).perform(click());
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    // test nav to register page
    @Test
    public void test_navRegisterActivity() {
        onView(allOf(withId(R.id.register), isDisplayed())).perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

    // test nav to register page
    @Test
    public void test_registerActivityBackLogin() {
        onView(allOf(withId(R.id.register), isDisplayed())).perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
//        onView(withId(R.id.logOutButton)).perform(click());
        pressBack();
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }


}
