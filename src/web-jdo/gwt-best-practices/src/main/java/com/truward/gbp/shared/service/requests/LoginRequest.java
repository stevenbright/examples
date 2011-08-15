package com.truward.gbp.shared.service.requests;

import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.responses.LoginResponse;

/**
 * Identifies login request
 */
public final class LoginRequest implements ServiceRequest<LoginResponse> {
    private String username;
    private String password;


    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
