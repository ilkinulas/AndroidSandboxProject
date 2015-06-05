package net.peakgames.sandbox.di;


import net.peakgames.mobile.android.log.AndroidLogger;
import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.di.annotations.LoggerLevel;
import net.peakgames.sandbox.di.annotations.LoggerTag;
import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.network.ChatServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatAppModule {

    private ChatApp application;

    public ChatAppModule(ChatApp app) {
        this.application = app;
    }

    @Provides
    @Singleton
    public ChatApp provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    public Logger provideLogger(@LoggerTag String tag, @LoggerLevel Logger.LogLevel level) {
        Logger logger = new AndroidLogger();
        logger.setTag(tag);
        logger.setLogLevel(level);
        return logger;
    }

    @Provides
    @Singleton
    public ChatService provideChatService() {
        return new ChatServiceImpl();
    }

}
