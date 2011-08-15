package com.truward.gbp.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.truward.gbp.shared.service.ServiceRequest;
import com.truward.gbp.shared.service.ServiceResponse;

/**
 * Represents asynchronous aspect of the application service
 */
public interface AppServiceAsync {
    <T extends ServiceResponse> void process(ServiceRequest<T> action, AsyncCallback<T> async);
}
