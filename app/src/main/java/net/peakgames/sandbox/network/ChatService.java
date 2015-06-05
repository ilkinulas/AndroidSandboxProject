package net.peakgames.sandbox.network;

import rx.Observable;

public interface ChatService {

    enum ConnectionState {
        CONNNECTED, DISCONNECTED
    }

    interface ChatServiceListener {
        void onConnectionStateChanged(ConnectionState state);
        void onMessageReceived(String message);
    }

    Observable<LoginResponse> connect(String channelName);
    Observable<String> messages();
    void sendMessage(String message);
}
