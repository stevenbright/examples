package com.truward.gbp.server.processors;

import com.truward.gbp.shared.service.ServiceException;
import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.responses.GotLoginStateResponse;

/**
 * Checks login state
 */
public class GetLoginStateProcessor implements Processor<GotLoginStateResponse> {
    public GotLoginStateResponse processRequest(ServiceRequest<GotLoginStateResponse> request, Context context) throws ServiceException {
        return new GotLoginStateResponse(context.getCurrentUser() != null);
    }
}

