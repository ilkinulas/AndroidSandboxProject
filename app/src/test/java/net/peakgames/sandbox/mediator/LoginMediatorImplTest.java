package net.peakgames.sandbox.mediator;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.network.LoginResponse;
import net.peakgames.sandbox.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginMediatorImplTest {

    private static final String LOGIN_FAILED_MESSAGE = "xxxxxxxxx";
    private static final String USERNAME = "ilkin";

    private LoginViewMediatorImpl mediator;

    @Mock private Logger logger;
    @Mock private ChatService chatService;
    @Mock private LoginView loginView;

    private Observable<LoginResponse> loginSuccess = Observable.create(new Observable.OnSubscribe<LoginResponse>() {
        @Override
        public void call(Subscriber<? super LoginResponse> subscriber) {
            subscriber.onNext(LoginResponse.success());
            subscriber.onCompleted();
        }
    });

    private Observable<LoginResponse> loginFailed = Observable.create(new Observable.OnSubscribe<LoginResponse>() {
        @Override
        public void call(Subscriber<? super LoginResponse> subscriber) {
            subscriber.onNext(LoginResponse.failed(LOGIN_FAILED_MESSAGE));
            subscriber.onCompleted();
        }
    });

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mediator = new LoginViewMediatorImpl(logger, chatService);
        mediator.setView(loginView);
    }

    @Test
    public void loginFailure() {
        when(chatService.connect(Mockito.any(String.class))).thenReturn(loginFailed);
        mediator.onStartChatButtonClicked(USERNAME);
        verify(loginView).showConnectingIndicator();
        verify(loginView).onLoginFailed(LOGIN_FAILED_MESSAGE);
    }

    @Test
    public void setLoginSuccess() {
        when(chatService.connect(Mockito.any(String.class))).thenReturn(loginSuccess);
        mediator.onStartChatButtonClicked(USERNAME);
        verify(loginView).showConnectingIndicator();
        verify(loginView).onLoginSuccess();
    }

    @Test
    public void shouldUnsubscribeOnPause() {
        assertNull(mediator.getLoginResponseSubscription());

        when(chatService.connect(Mockito.any(String.class))).thenReturn(loginSuccess);
        mediator.onStartChatButtonClicked(USERNAME);
        Subscription subscription = mediator.getLoginResponseSubscription();
        assertTrue(subscription.isUnsubscribed());

        mediator.onPause();
        assertTrue(subscription.isUnsubscribed());
    }
}
