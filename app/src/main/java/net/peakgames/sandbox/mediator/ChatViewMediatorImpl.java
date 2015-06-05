package net.peakgames.sandbox.mediator;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.view.ChatView;
import net.peakgames.sandbox.view.ViewInterface;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;

public class ChatViewMediatorImpl implements ChatViewMediator {

    private ChatView chatView;
    private ChatService chatService;
    private Logger log;
    private Subscription subscription;


    @Inject
    public ChatViewMediatorImpl(Logger log, ChatService chatService) {
        this.log = log;
        this.chatService = chatService;
        this.subscription = this.chatService.messages().subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                chatView.onNewChatMessage(s);
            }
        });
    }

    @Override
    public void sendMessage(String message) {
        chatService.sendMessage(message);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        this.chatView = null;
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setView(ViewInterface view) {
        this.chatView = (ChatView) view;
    }
}
