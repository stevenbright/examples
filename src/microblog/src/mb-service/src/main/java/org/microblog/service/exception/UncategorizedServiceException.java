package org.microblog.service.exception;

import org.microblog.exposure.server.exception.ServiceException;

/**
 * Represents uncategorized service exception.
 */
public class UncategorizedServiceException extends ServiceException {
    public UncategorizedServiceException() {
    }

    public UncategorizedServiceException(String message) {
        super(message);
    }

    public UncategorizedServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncategorizedServiceException(Throwable cause) {
        super(cause);
    }
}
