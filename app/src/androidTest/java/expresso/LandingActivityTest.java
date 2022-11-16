package expresso;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
    public ActivityTestRule<LandingPage> activityRule
            = new ActivityTestRule<>(
            LandingPage.class,
            true,     // initialTouchMode
            false);   // launchActivity. False to customize the intent
//    public ActivityScenarioRule<LandingPage> activityRule =
//            new ActivityScenarioRule<LandingPage>(LandingPage.class);

    @Test
    public void intent() {
        Intent intent = new Intent();
//        intent.putExtra("your_key", "your_value");

        activityRule.launchActivity(intent);

        // Continue with your test
    }


    @Test
    public void test_isTitleTextDisplayed() {
        signIn("tester5@gmail.com", "tester5");
//        onView(withId(R.id.userEmail)).check(matches(withText("tester5@gmail.com")));
//        signIn("tester5@gmail.com", "tester5");



    }

    private void signIn(String email, String password) {
               mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                        }
                    }
                });
    }
}
