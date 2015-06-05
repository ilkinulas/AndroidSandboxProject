package net.peakgames.sandbox.mediator;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.network.LoginResponse;
import net.peakgames.sandbox.view.LoginView;
import net.peakgames.sandbox.view.ViewInterface;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

public class LoginViewMediatorImpl implements LoginViewMediator {

    private Logger log;
    private LoginView loginView;
    private ChatService chatService;
    private Subscription loginResponseSubscription;

    @Inject
    public LoginViewMediatorImpl(Logger log, ChatService chatService) {
        this.log = log;
        this.chatService = chatService;
        log.d("LoginViewMediatorImpl created.");
    }


    @Override
    public void onStartChatButtonClicked(String userName) {
        log.i("Start chat... username : " + userName);
        loginView.showConnectingIndicator();

        loginResponseSubscription = chatService.connect("ilkin-test").subscribe(new Subscriber<LoginResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LoginResponse loginResponse) {
                if (loginResponse.isSuccess()) {
                    loginView.onLoginSuccess();
                } else {
                    loginView.onLoginFailed(loginResponse.getMessage());
                }
            }
        });
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        this.loginView = null;
        if (loginResponseSubscription != null) {
            loginResponseSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setView(ViewInterface view) {
        this.loginView = (LoginView) view;
    }

    Subscription getLoginResponseSubscription() {
        return loginResponseSubscription;
    }
}
