package net.peakgames.sandbox.network;

import rx.Observable;

public class ChatServiceImpl implements ChatService {

    @Override
    public Observable<LoginResponse> connect(String channelName) {
        return null;
    }

    @Override
    public Observable<String> messages() {
        return null;
    }

    @Override
    public void sendMessage(String message) {

    }
}
