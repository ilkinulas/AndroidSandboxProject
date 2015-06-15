package net.peakgames.sandbox;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.UiThreadTest;

import net.peakgames.sandbox.di.ChatAppComponent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private LoginActivity activity;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule(LoginActivity.class);

    @Before
    public void setup() {
        getChatApp().initComponentAndInjectForUITest();
        ChatAppComponent.Initializer.init(getChatApp(), true);
        activity = activityRule.getActivity();
    }

    private ChatApp getChatApp() {
        return (ChatApp) activityRule.getActivity().getApplication();
    }

    @Test
    public void display_error_message_if_username_is_empty() {
        onView(withId(R.id.username_edittext)).perform(clearText());
        onView(withId(R.id.login_button)).perform(click());
        onView(withId(R.id.error_textview)).check(matches(withText(R.string.error_empty_username)));
    }

    @UiThreadTest
    public void testLoginSuccess() {
        activity = activityRule.getActivity();
        activity.onLoginSuccess();
        onView(withId(R.id.send_chat_message_button)).check(matches(isDisplayed()));
    }

    @Test
    public void chatShouldStartAfterValidUserName() {
        String username = "tayyip";
        onView(withId(R.id.username_edittext)).perform(typeText(username));
        onView(withId(R.id.login_button)).perform(click());

        Mockito.verify(activity.getLoginMediator()).onStartChatButtonClicked(username);
    }
}
