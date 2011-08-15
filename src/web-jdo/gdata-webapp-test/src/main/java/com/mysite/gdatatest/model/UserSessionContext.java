package com.mysite.gdatatest.model;

/**
 * Represents the particular user's context.
 */
public final class UserSessionContext {
    private final String googleCode;

    public String getGoogleCode() {
        return googleCode;
    }

    public UserSessionContext(String googleCode) {
        this.googleCode = googleCode;
    }
}
