package net.peakgames.sandbox;

import android.app.Activity;

public class BaseActivity extends Activity {
    protected ChatApp getChatApplication() {
        return (ChatApp) getApplication();
    }
}
