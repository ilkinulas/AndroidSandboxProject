package net.peakgames.sandbox;


import android.app.Application;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.di.ChatAppComponent;

import javax.inject.Inject;

public class ChatApp extends Application {

    private ChatAppComponent chatAppComponent;

    @Inject
    Logger log;

    @Override
    public void onCreate() {
        super.onCreate();
        initComponentAndInject();

        log.i("Chat application created.");
    }

    public void initComponentAndInject() {
        chatAppComponent = ChatAppComponent.Initializer.init(this, false);
        chatAppComponent.inject(this);
    }

    public void initComponentAndInjectForUITest() {
        chatAppComponent = ChatAppComponent.Initializer.init(this, true);
        chatAppComponent.inject(this);
    }

    public ChatAppComponent getChatAppComponent() {
        return chatAppComponent;
    }
}
