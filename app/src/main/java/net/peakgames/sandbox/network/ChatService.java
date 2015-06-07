package net.peakgames.sandbox.network;

import rx.Observable;

public interface ChatService {
    Observable<LoginResponse> connect(String channelName);
    Observable<String> messages();
    void sendMessage(String message);
}
