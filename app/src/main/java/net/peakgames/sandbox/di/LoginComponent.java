package net.peakgames.sandbox.di;


import net.peakgames.sandbox.ChatApp;
import net.peakgames.sandbox.LoginActivity;
import net.peakgames.sandbox.di.annotations.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = ChatAppComponent.class, modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);

    class Initializer {
        public static final LoginComponent init(ChatApp application) {
            return DaggerLoginComponent.builder()
                    .chatAppComponent(application.getChatAppComponent())
                    .build();
        }
    }

}
