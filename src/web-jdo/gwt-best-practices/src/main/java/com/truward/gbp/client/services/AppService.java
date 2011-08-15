package com.truward.gbp.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.truward.gbp.shared.service.ServiceException;
import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.ServiceResponse;

/**
 * Represents application's core service
 */
@RemoteServiceRelativePath("AppService")
public interface AppService extends RemoteService {
    <T extends ServiceResponse> T process(ServiceRequest<T> action) throws ServiceException;
}
