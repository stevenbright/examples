package com.truward.web.model;



public class AuthParameters {
    private final String permanentToken;

    private final long memberId;

    public String getPermanentToken() {
        return permanentToken;
    }

    public long getMemberId() {
        return memberId;
    }

    public AuthParameters(String permanentToken, long memberId) {
        this.permanentToken = permanentToken;
        this.memberId = memberId;
    }
}
