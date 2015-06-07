package net.peakgames.sandbox.data;

import android.content.SharedPreferences;

public class BooleanPreference extends BasePreference {


    private final boolean defaultValue;

    public BooleanPreference(SharedPreferences preferences, String key) {
        this(preferences, key, false);
    }

    public BooleanPreference(SharedPreferences preferences, String key, boolean defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public boolean get() {
        return preferences.getBoolean(key, defaultValue);
    }


    public void set(boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

}
