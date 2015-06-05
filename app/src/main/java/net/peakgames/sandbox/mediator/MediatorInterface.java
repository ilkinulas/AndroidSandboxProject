package net.peakgames.sandbox.mediator;

import net.peakgames.sandbox.view.ViewInterface;

public interface MediatorInterface {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
    void setView(ViewInterface view);

}
