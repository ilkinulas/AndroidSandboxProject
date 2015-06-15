package net.peakgames.sandbox.di;

import net.peakgames.sandbox.ChatActivity;
import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DebugChatAppModule.class, DebugConfigModule.class})
public interface ChatAppComponent {

    void inject(ChatApp app);
    void inject(LoginActivity loginActivity);
    void inject(ChatActivity chatActivity);

    class Initializer {
        public static ChatAppComponent init(ChatApp app, boolean uiTestMode) {
            return DaggerChatAppComponent.builder()
                    .debugChatAppModule(new DebugChatAppModule(app, uiTestMode))
                    .build();
        }
    }
}
