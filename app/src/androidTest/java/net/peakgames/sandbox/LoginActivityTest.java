package net.peakgames.sandbox;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;

import net.peakgames.sandbox.di.ChatAppComponent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {


    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule(LoginActivity.class);

    @Before
    public void setup() {
        ChatAppComponent.Initializer.init(getChatApp());
    }

    private ChatApp getChatApp() {
        return (ChatApp) activityRule.getActivity().getApplication();
    }

    @Test
    public void display_error_message_if_username_is_empty() {
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.error_textview)).check(matches(withText(R.string.error_empty_username)));
    }

    @UiThreadTest
    public void testLoginSuccess() {
        final LoginActivity activity = activityRule.getActivity();
        activity.onLoginSuccess();
        onView(withId(R.id.send_chat_message_button)).check(matches(isDisplayed()));
    }
}
