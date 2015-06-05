package net.peakgames.sandbox.view;

public interface LoginView extends ViewInterface {
    void showConnectingIndicator();
    void onLoginSuccess();
    void onLoginFailed(String error);

}
