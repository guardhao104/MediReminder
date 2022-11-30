package expresso;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

import android.app.Activity;
import android.os.SystemClock;
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
public class UITest {
    private  String[] names = {"", "a", "123123", "tester5@gmail.com"};
    private  String[] pwds = {"", "a", "123123", "tester5"};

    private  String[] registerName =        {"", "a", "123", "", "Andy Andy", "Andy", "Andy", ""};
    private  String[] registerAge =         {"", "1", "123", "1", "1", "1", "", "1"};
    private  String[] registerEmail =       {"", "a", "123", "tester6@gmail.com", "tester6@gmail.com", "","tester6@gmail.com","tester6@gmail.com"};
    private  String[] registerPassword =    {"", "a", "123", "tester6", "", "tester6", "tester6", "tester6"};

    final int listCount = 12;

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
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("abc"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("abc"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("123123"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("123123"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(" "), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("123123"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("123123"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(" "), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(" "), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(" "), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    /*
    * register page
    */

    @Test
    // test nav to register page
    public void test_navRegisterActivity() {
        onView(allOf(withId(R.id.register), isDisplayed())).perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isRegisterView() {
        test_navRegisterActivity();
        onView(withId(R.id.banner)).check(matches(isDisplayed()));
        onView(withId(R.id.bannerDescription)).check(matches(isDisplayed()));
        onView(withId(R.id.fullName)).check(matches(isDisplayed()));
        onView(withId(R.id.age)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
        onView(withId(R.id.password)).check(matches(isDisplayed()));
        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
    }

    @Test
    public void test_incorrectRegister() {
        test_navRegisterActivity();

        for(int i = 0; i < registerName.length; i++) {
            onView(allOf(withId(R.id.fullName), isDisplayed())).perform(replaceText(registerName[i]), closeSoftKeyboard());
            onView(allOf(withId(R.id.age), isDisplayed())).perform(replaceText(registerAge[i]), closeSoftKeyboard());
            onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText(registerEmail[i]), closeSoftKeyboard());
            onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText(registerPassword[i]), closeSoftKeyboard());

            onView(allOf(withId(R.id.registerUser), isDisplayed())).perform(click());
        }

        onView(withId(R.id.register)).check(matches(isDisplayed()));
    }

    @Test
    public  void test_isRegisterViewTextDisplay() {
        test_navRegisterActivity();
        onView(withId(R.id.banner)).check(matches(withText("MediReminder")));
        onView(withId(R.id.bannerDescription)).check(matches(withText("Reminder App")));
        onView(withId(R.id.fullName)).check(matches(withHint("Full Name")));
        onView(withId(R.id.age)).check(matches(withHint("Age")));
        onView(withId(R.id.email)).check(matches(withHint("@+string/prompt_email")));
        onView(withId(R.id.password)).check(matches(withHint("@+string/prompt_password")));
    }

    // test nav to register page
    public void test_registerActivityBackLogin() {
        onView(allOf(withId(R.id.register), isDisplayed())).perform(click());
        onView(withId(R.id.register)).check(matches(isDisplayed()));
//        onView(withId(R.id.logOutButton)).perform(click());
        pressBack();
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    /*
    * landing
    * */

    private void navToLandingActivity() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
    }

    // nav to landing page
    @Test
    public void test_navLandingPageActivity() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(withId(R.id.landing)).check(matches(isDisplayed()));
    }

    // nav to landing page and back to login page
    @Test
    public void test_LandingPageActivityBackLogin() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(withId(R.id.logOutButton)).perform(click());
        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isLandingActivityInView() {
        navToLandingActivity();
        onView((withId(R.id.landing))).check(matches(isDisplayed()));
        onView(withId(R.id.NameTV)).check(matches((isDisplayed())));
        onView(withId(R.id.EmailTV)).check(matches(isDisplayed()));
        onView(withId(R.id.userName)).check(matches(isDisplayed()));
        onView(withId(R.id.userEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.logOutButton)).check(matches(isDisplayed()));
        onView(withId(R.id.nextPageButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test_patientListActicityBackLandingPage() {
        navToLandingActivity();
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());
        onView(withId(R.id.patient_list)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.landing)).check(matches(isDisplayed()));
    }

    @Test
    public void test_navPatientListActivity() {
        navToLandingActivity();
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());
        onView(withId(R.id.patient_list)).check(matches(isDisplayed()));
    }

    /*
    * patientListActivity
    * */

    public void navToPatientListActivity() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());
        onView(withId(R.id.patient_list)).check(matches(isDisplayed()));
    }

    // check all components are in the view
    @Test
    public void test_isPatientListActivityInView() {
        navToPatientListActivity();
        onView(withId(R.id.list_view_search)).check(matches(isDisplayed()));
        onView(withId(R.id.list_view_items)).check(matches(isDisplayed()));
    }

//    @Test
//    public void test_searchListItemDisplay() {
//        navToPatientListActivity();
//        onView(allOf(withId(R.id.list_view_search), isDisplayed())).perform(replaceText("To"), closeSoftKeyboard());
//        SystemClock.sleep(10000);
//    }

    // select list item, nav to detail
    @Test
    public void test_selectListItemToDetail() {
        navToPatientListActivity();

        SystemClock.sleep(1000);

        for(int i = 0; i < listCount; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(i).perform(click());
            onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));

            onView((withId(R.id.patient_name))).check(matches(isDisplayed()));
            onView(withId(R.id.addReminderButton)).check(matches(isDisplayed()));
            onView(withId(R.id.list_view_items)).check(matches(isDisplayed()));

            pressBack();
        }
    }

    /*
    * Patient_info
    * */
    public void navToPatientListInfoActivity() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());

        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(11).perform(click());
        onView(allOf(withId(R.id.addReminderButton), isDisplayed())).perform(click());
    }

    @Test
    public void test_isCreateReminderActivityInView() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("tester5@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("tester5"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());

        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(11).perform(click());
        onView(allOf(withId(R.id.addReminderButton), isDisplayed())).perform(click());

//        SystemClock.sleep(1000);
        onView((withId(R.id.createReminderActivity))).check(matches(isDisplayed()));
        onView((withId(R.id.nameTV))).check(matches(isDisplayed()));
        onView((withId(R.id.nameET))).check(matches(isDisplayed()));
        onView((withId(R.id.medicineTV))).check(matches(isDisplayed()));
        onView((withId(R.id.medicineET))).check(matches(isDisplayed()));
        onView((withId(R.id.dateTV))).check(matches(isDisplayed()));
        onView((withId(R.id.editTextDate))).check(matches(isDisplayed()));
        onView((withId(R.id.timeTV))).check(matches(isDisplayed()));
        onView((withId(R.id.editTextTime))).check(matches(isDisplayed()));
        onView((withId(R.id.submitBtn))).check(matches(isDisplayed()));
    }

    /*
    * PatientView
    * */
    public void navToViewReminderActivity() {
        onView(allOf(withId(R.id.email), isDisplayed())).perform(replaceText("andy@gmail.com"), closeSoftKeyboard());
        onView(allOf(withId(R.id.password), isDisplayed())).perform(replaceText("andy123"), closeSoftKeyboard());
        onView(allOf(withId(R.id.signIn), isDisplayed())).perform(click());
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());
        onView(withId(R.id.reminder)).check(matches(isDisplayed()));
    }

    @Test
    public void test_isReminderActivityInView() {
        navToViewReminderActivity();
        onView((withId(R.id.patientName))).check(matches(isDisplayed()));
        onView((withId(R.id.reminderListView))).check(matches(isDisplayed()));
    }
}
