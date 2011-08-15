package com.alexshabanov.restcomp.exposure.server.exception;


/**
 * Represents an exception when the requested service is not found.
 */
public class ServiceNotFoundException extends ServiceException {
    public ServiceNotFoundException() {
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }

    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNotFoundException(Throwable cause) {
        super(cause);
    }
}
