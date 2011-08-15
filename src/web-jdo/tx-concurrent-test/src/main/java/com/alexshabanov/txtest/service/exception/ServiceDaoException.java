package com.alexshabanov.txtest.service.exception;


public final class ServiceDaoException extends ServiceException {
    public ServiceDaoException() {
    }

    public ServiceDaoException(String message) {
        super(message);
    }

    public ServiceDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceDaoException(Throwable cause) {
        super(cause);
    }
}
