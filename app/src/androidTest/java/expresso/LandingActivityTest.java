package expresso;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.medireminder.LandingPage;
import com.example.medireminder.R;
import com.example.medireminder.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LandingActivityTest {
    private FirebaseAuth mAuth;

    @Rule
    public ActivityScenarioRule<LandingPage> activityRule =
            new ActivityScenarioRule<LandingPage>(LandingPage.class);

    @Test
    public void test_isActivityInView() {
        onView((withId(R.id.landing))).check(matches(isDisplayed()));
        onView(withId(R.id.NameTV)).check(matches((isDisplayed())));
        onView(withId(R.id.EmailTV)).check(matches(isDisplayed()));
        onView(withId(R.id.userName)).check(matches(isDisplayed()));
        onView(withId(R.id.userEmail)).check(matches(isDisplayed()));
        onView(withId(R.id.logOutButton)).check(matches(isDisplayed()));
        onView(withId(R.id.nextPageButton)).check(matches(isDisplayed()));
    }

    @Test
    public void test_navPatientListActivity() {
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());
        onView(withId(R.id.patient_list)).check(matches(isDisplayed()));
    }

    @Test
    public void test_patientListActicityBackLandingPage() {
        onView(allOf(withId(R.id.nextPageButton), isDisplayed())).perform(click());
        onView(withId(R.id.patient_list)).check(matches(isDisplayed()));
        pressBack();
        onView(withId(R.id.landing)).check(matches(isDisplayed()));
    }

}
