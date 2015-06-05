package net.peakgames.sandbox.di;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.di.annotations.PerActivity;
import net.peakgames.sandbox.mediator.ChatViewMediator;
import net.peakgames.sandbox.mediator.ChatViewMediatorImpl;
import net.peakgames.sandbox.network.ChatService;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatModule {

    @Provides
    @PerActivity
    public ChatViewMediator provideChatViewMediator(Logger log, ChatService chatService) {
        return new ChatViewMediatorImpl(log,  chatService);
    }

}
