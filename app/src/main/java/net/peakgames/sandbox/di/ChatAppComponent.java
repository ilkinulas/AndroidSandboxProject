package net.peakgames.sandbox.di;

import net.peakgames.sandbox.ChatActivity;
import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ChatAppModule.class, ConfigModule.class})
public interface ChatAppComponent {

    void inject(ChatApp app);
    void inject(LoginActivity loginActivity);
    void inject(ChatActivity chatActivity);

    class Initializer {
        public static ChatAppComponent init(ChatApp app, boolean uiTestMode) {
            return DaggerChatAppComponent.builder()
                    .chatAppModule(new ChatAppModule(app, uiTestMode))
                    .build();
        }
    }
}
