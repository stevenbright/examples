package com.truward.gbp.server.processors;

import com.truward.gbp.shared.service.Errors;
import com.truward.gbp.shared.service.ServiceException;
import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.requests.LoginRequest;
import com.truward.gbp.shared.service.responses.LoginResponse;

/**
 * Processes login requests
 */
public class LoginProcessor implements Processor<LoginResponse> {
    public LoginResponse processRequest(ServiceRequest<LoginResponse> request,
                                        Context context) throws ServiceException {
        if (context.getCurrentUser() != null) {
            // there is no need to login twice
            throw new ServiceException(Errors.INTERNAL_FAILURE);
        }

        final LoginRequest loginRequest = (LoginRequest) request;
        final String username = loginRequest.getUsername();
        
        if (!context.getDao().isCredentialsValid(username, loginRequest.getPassword())) {
            throw new ServiceException(Errors.INVALID_CREDENTIALS);
        }

        context.setCurrentUser(username);

        return new LoginResponse();
    }
}
