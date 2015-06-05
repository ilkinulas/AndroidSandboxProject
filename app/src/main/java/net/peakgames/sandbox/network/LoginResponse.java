package net.peakgames.sandbox.network;

public class LoginResponse {
    private boolean success;
    private String message;

    public static LoginResponse success() {
        LoginResponse response = new LoginResponse();
        response.success = true;
        return response;
    }

    public static LoginResponse failed(String message) {
        LoginResponse response = new LoginResponse();
        response.success = false;
        response.message = message;
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
