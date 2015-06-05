package net.peakgames.sandbox.network;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatServiceImpl implements ChatService {

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private AtomicInteger counter = new AtomicInteger();
    private Subscriber<? super String> chatServiceSubscriber;

    @Override
    public Observable<LoginResponse> connect(String channelName) {
        return Observable.create(new Observable.OnSubscribe<LoginResponse>() {
            @Override
            public void call(final Subscriber<? super LoginResponse> subscriber) {
                executor.schedule(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.onNext(LoginResponse.success());
                        subscriber.onCompleted();
                    }
                }, 1, TimeUnit.SECONDS);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String> messages() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                chatServiceSubscriber = subscriber;
                
                startDebugMessages();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private void startDebugMessages() {
        while (true) {
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {}
            chatServiceSubscriber.onNext("new message " + counter.incrementAndGet());
            if (Math.random() > 0.95f) {
                chatServiceSubscriber.onCompleted();
            }
        }
    }

    @Override
    public void sendMessage(final String message) {

    }


}
