package net.peakgames.sandbox.di;

import android.content.SharedPreferences;

import net.peakgames.mobile.android.log.AndroidLogger;
import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.data.BooleanPreference;
import net.peakgames.sandbox.di.annotations.LoggerLevel;
import net.peakgames.sandbox.di.annotations.LoggerTag;
import net.peakgames.sandbox.di.annotations.MockMode;
import net.peakgames.sandbox.mediator.ChatViewMediator;
import net.peakgames.sandbox.mediator.ChatViewMediatorImpl;
import net.peakgames.sandbox.mediator.LoginViewMediator;
import net.peakgames.sandbox.mediator.LoginViewMediatorImpl;
import net.peakgames.sandbox.network.ChatService;
import net.peakgames.sandbox.network.ChatServiceImpl;
import net.peakgames.sandbox.network.mock.MockChatService;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.MODE_PRIVATE;

@Module
public class DebugChatAppModule {
    private ChatApp application;
    private boolean uiTesting;

    public DebugChatAppModule(ChatApp app, boolean uiTesting) {
        this.application = app;
        this.uiTesting = uiTesting;
    }

    @Singleton
    @Provides
    public ChatApp provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    public SharedPreferences provideSharedPreferences(ChatApp app) {
        return app.getSharedPreferences("KimbilirChallenge", MODE_PRIVATE);
    }

    @Singleton
    @Provides
    @MockMode
    public BooleanPreference provideMockModePreference(SharedPreferences sharedPreferences) {
        return new BooleanPreference(sharedPreferences, "is_mock_mode", false);
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
    public ChatService provideChatService(@MockMode BooleanPreference isMockMode, Logger log) {
        if (isMockMode.get()) {
            log.d("MOCK Chat service created");
            return new MockChatService();
        } else {
            log.d("REAL Chat service created");
            return new ChatServiceImpl();
        }
    }

    @Provides
    @Singleton
    public LoginViewMediator provideLoginViewMediator(Logger log, ChatService chatService) {
        if (this.uiTesting) {
            return Mockito.mock(LoginViewMediator.class);
        } else {
            return new LoginViewMediatorImpl(log, chatService);
        }
    }

    @Provides
    @Singleton
    public ChatViewMediator provideChatViewMediator(Logger log, ChatService chatService) {
        return new ChatViewMediatorImpl(log, chatService);
    }
}
