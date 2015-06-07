package net.peakgames.sandbox.network;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatServiceImpl implements ChatService  {
    private Subscriber<? super String> chatServiceSubscriber;

    @Override
    public Observable<LoginResponse> connect(String channelName) {
        return Observable.create(new Observable.OnSubscribe<LoginResponse>() {
            @Override
            public void call(Subscriber<? super LoginResponse> subscriber) {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {}
                subscriber.onNext(LoginResponse.success());
                subscriber.onCompleted();
            }
        });
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

        chatServiceSubscriber.onNext(message.toUpperCase());
    }
}
