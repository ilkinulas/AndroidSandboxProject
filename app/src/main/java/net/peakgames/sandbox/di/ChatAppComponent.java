package net.peakgames.sandbox.di;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.config.di.ConfigModule;
import net.peakgames.sandbox.network.ChatService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ChatAppModule.class, ConfigModule.class})
public interface ChatAppComponent {

    void inject(ChatApp app);

    Logger logger();
    ChatApp application();
    ChatService chatService();

    class Initializer {
        public static ChatAppComponent init(ChatApp app) {
            return DaggerChatAppComponent.builder()
                    .chatAppModule(new ChatAppModule(app))
                    .build();
        }
    }
}
