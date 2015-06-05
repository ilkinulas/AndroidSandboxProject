package net.peakgames.sandbox.di;


import net.peakgames.sandbox.ChatActivity;
import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.di.annotations.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ChatAppComponent.class, modules = ChatModule.class)
public interface ChatComponent {

    void inject(ChatActivity activity);

    class Initializer {
        public static final ChatComponent init(ChatApp application) {
            return DaggerChatComponent.builder()
                    .chatAppComponent(application.getChatAppComponent())
                    .build();
        }
    }
}
