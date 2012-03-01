package com.alexshabanov.springintdemo.service.impl.apns.model;

public class ApnsPayload {
    private final String deviceToken;
    private final String message;
    private final int badge;

    public ApnsPayload(String deviceToken, String message, int badge) {
        this.deviceToken = deviceToken;
        this.message = message;
        this.badge = badge;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getMessage() {
        return message;
    }

    public int getBadge() {
        return badge;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(64);
        sb.append("ApnsPayload");
        sb.append("{deviceToken='").append(getDeviceToken()).append('\'');
        sb.append(", message='").append(getMessage()).append('\'');
        sb.append(", badge=").append(getBadge());
        sb.append('}');
        return sb.toString();
    }
}
