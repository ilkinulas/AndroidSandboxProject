package net.peakgames.sandbox;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.peakgames.sandbox.di.ChatComponent;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class ChatActivityTest {

    @Rule
    public ActivityTestRule<ChatActivity> activityRule =
            new ActivityTestRule(ChatActivity.class);

    private ChatApp getChatApp() {
        return (ChatApp) activityRule.getActivity().getApplication();
    }

    @Before
    public void setup() {
        ChatComponent.Initializer.init(getChatApp());
    }

    @Test
    public void test1() {
        onView(withId(R.id.chat_message_edit_text)).perform(clearText());
        onView(withId(R.id.send_chat_message_button)).check(matches(not(isEnabled())));

        onView(withId(R.id.chat_message_edit_text)).perform(typeText("hello"));
        onView(withId(R.id.send_chat_message_button)).check(matches(isEnabled()));

        onView(withId(R.id.send_chat_message_button)).perform(click());

    }
}