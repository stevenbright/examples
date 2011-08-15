package com.truward.gbp.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Base class for all the service requests invoked by user on the client side.
 * This is a part of service request-response framework.
 * @param <T> ServiceResponse to the command.
 */
public interface ServiceRequest<T extends ServiceResponse> extends IsSerializable {
}
