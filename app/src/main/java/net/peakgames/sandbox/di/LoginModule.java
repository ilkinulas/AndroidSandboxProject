package net.peakgames.sandbox.di;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.di.annotations.PerActivity;
import net.peakgames.sandbox.mediator.LoginViewMediator;
import net.peakgames.sandbox.mediator.LoginViewMediatorImpl;
import net.peakgames.sandbox.network.ChatService;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    @PerActivity
    public LoginViewMediator provideLoginViewMediator(Logger log, ChatService chatService) {
        return new LoginViewMediatorImpl(log,  chatService);
    }

}
