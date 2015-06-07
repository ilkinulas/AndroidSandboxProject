package net.peakgames.sandbox.data;

import android.content.SharedPreferences;

public class StringPreference extends BasePreference {

    private final String defaultValue;

    public StringPreference(SharedPreferences preferences, String key, String defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String get() {
        return preferences.getString(key, defaultValue);
    }

    public void set(String value) {
        preferences.edit().putString(key, value).apply();
    }

}
