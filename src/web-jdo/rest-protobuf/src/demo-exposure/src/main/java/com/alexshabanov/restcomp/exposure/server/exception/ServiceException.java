package com.alexshabanov.restcomp.exposure.server.exception;

/**
 * Base class for all the service exceptions.
 */
public abstract class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}