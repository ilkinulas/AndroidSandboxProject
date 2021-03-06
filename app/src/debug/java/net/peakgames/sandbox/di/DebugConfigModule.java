package net.peakgames.sandbox.di;

import net.peakgames.mobile.android.log.Logger;
import net.peakgames.sandbox.di.annotations.LoggerLevel;
import net.peakgames.sandbox.di.annotations.LoggerTag;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DebugConfigModule {

    @Provides
    @Singleton
    @LoggerTag
    public String provideLoggerTag() {
        return "ChatApp-Debug";
    }

    @Provides
    @Singleton
    @LoggerLevel
    public Logger.LogLevel provideLoggerLevel() {
        return Logger.LogLevel.DEBUG;
    }
}
