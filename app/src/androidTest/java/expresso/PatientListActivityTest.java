package expresso;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.JMock1Matchers.equalTo;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import android.os.SystemClock;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.medireminder.LandingPage;
import com.example.medireminder.R;
import com.example.medireminder.patient_info.PatientInfoListActivity;
import com.example.medireminder.patient_list.PatientListActivity;
import com.example.medireminder.patient_list.PatientListAdapter;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PatientListActivityTest {

    @Rule
    public ActivityScenarioRule<PatientListActivity> activityRule =
            new ActivityScenarioRule<PatientListActivity>(PatientListActivity.class);

    // check all components are in the view
    @Test
    public void test_isActivityInView() {
        onView((withId(R.id.patient_list))).check(matches(isDisplayed()));
        onView(withId(R.id.list_view_search)).check(matches(isDisplayed()));
        onView(withId(R.id.list_view_items)).check(matches(isDisplayed()));
    }

    // select list item, nav to detail
    @Test
    public void test_selectListItemToDetail() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(0).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(1).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(2).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(3).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(4).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(5).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
        onData(anything()).inAdapterView(withId(R.id.list_view_items)).atPosition(6).perform(click());
        onView((withId(R.id.patient_list_info))).check(matches(isDisplayed()));
        pressBack();
    }
}
