package net.peakgames.sandbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ViewAnimator;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.data.BooleanPreference;
import net.peakgames.sandbox.di.annotations.MockMode;
import net.peakgames.sandbox.mediator.LoginViewMediator;
import net.peakgames.sandbox.view.LoginView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class LoginActivity extends BaseActivity implements LoginView {

    @InjectView(R.id.view_animator)  ViewAnimator viewAnimator;
    @InjectView(R.id.username_edittext) EditText usernameEditText;
    @InjectView(R.id.login_form_container) View loginFormContainer;
    @InjectView(R.id.connecting_indicator) View connectingIndicator;
    @InjectView(R.id.error_textview) TextView errorTextView;
    @InjectView(R.id.mockModeSwitch) Switch mockModeSwitch;

    @Inject LoginViewMediator loginMediator;
    @Inject Logger log;
    @Inject @MockMode BooleanPreference mockMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        injectDependencies();
        mockModeSwitch.setChecked(mockMode.get());

        loginMediator.onCreate();

        viewAnimator.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        viewAnimator.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
    }

    @OnCheckedChanged(R.id.mockModeSwitch)
    public void onMockModeChanged(boolean checked) {
        if (mockMode.get() == checked) {
            return;
        }

        mockMode.set(checked);
        ChatApp app = getChatApplication();
        Intent newApp = new Intent(app, LoginActivity.class);
        newApp.setFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
        app.startActivity(newApp);
        app.initComponentAndInject();
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClick() {
        String username = usernameEditText.getText().toString();
        if (username == null || "".equals(username.trim())) {
            errorTextView.setText(R.string.error_empty_username);
        } else {
            loginMediator.onStartChatButtonClicked(username);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginMediator.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.loginMediator.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginMediator.onDestroy();
    }

    private void injectDependencies() {
        ButterKnife.inject(this);
        getChatApplication().getChatAppComponent().inject(this);
        this.loginMediator.setView(this);
    }

    @Override
    public void showConnectingIndicator() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(connectingIndicator));
    }

    @Override
    public void onLoginSuccess() {
        errorTextView.setText("");
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onLoginFailed(String error) {
        errorTextView.setText(error);
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(loginFormContainer));
    }

}