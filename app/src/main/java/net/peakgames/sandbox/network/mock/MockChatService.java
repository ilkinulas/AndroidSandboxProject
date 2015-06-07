package net.peakgames.sandbox.network.mock;

import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.network.LoginResponse;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MockChatService implements ChatService {

    private Subscriber<? super String> chatServiceSubscriber;

    @Override
    public Observable<LoginResponse> connect(String channelName) {
        return Observable.create(new Observable.OnSubscribe<LoginResponse>() {
            @Override
            public void call(Subscriber<? super LoginResponse> subscriber) {
                subscriber.onNext(LoginResponse.success());
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> messages() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                chatServiceSubscriber = subscriber;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void sendMessage(String message) {
        if (chatServiceSubscriber == null) {
            return;
        }

        chatServiceSubscriber.onNext("MOCK " + message.toUpperCase());
    }
}
