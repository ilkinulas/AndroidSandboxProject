package net.peakgames.sandbox;

import android.app.Activity;

//TODO try to use composition. Do not extend a custom made activity.
public class BaseActivity extends Activity {
    protected ChatApp getChatApplication() {
        return (ChatApp) getApplication();
    }
}
