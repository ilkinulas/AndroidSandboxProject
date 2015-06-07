package net.peakgames.sandbox.data;

import android.content.SharedPreferences;

public class BasePreference {
    protected SharedPreferences preferences;
    protected String key;

    public boolean isSet() {
        return preferences.contains(key);
    }

    public void delete() {
        preferences.edit().remove(key).apply();
    }
}
