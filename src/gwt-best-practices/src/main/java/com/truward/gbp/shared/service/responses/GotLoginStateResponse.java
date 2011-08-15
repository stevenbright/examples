package com.truward.gbp.shared.service.responses;

import com.truward.gbp.shared.service.ServiceResponse;

/**
 * Retrieved login state
 */
public final class GotLoginStateResponse
        implements ServiceResponse {
    private boolean loggedIn;

    public GotLoginStateResponse() {
    }

    public GotLoginStateResponse(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
