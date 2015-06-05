package net.peakgames.sandbox.mediator;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.view.ChatView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.Subscriber;

public class ChatViewMediatorImplTest {

    @Mock private Logger logger;
    @Mock private ChatService chatService;
    @Mock private ChatView chatView;

    private ChatViewMediatorImpl mediator;
    private Subscriber<? super String> chatMessageSubscriber;

    Observable<String> messages = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            chatMessageSubscriber = subscriber;
        }
    });

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(chatService.messages()).thenReturn(messages);

        mediator = new ChatViewMediatorImpl(logger, chatService);
        mediator.setView(chatView);
    }

    @Test
    public void viewShouldBeNotifiedIfNewMessageIsReceived() {
        String chatMessage = "what's up!";
        chatMessageSubscriber.onNext(chatMessage);
        Mockito.verify(chatView).onNewChatMessage(chatMessage);
    }


}
