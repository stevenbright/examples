package com.alexshabanov.txtest.service.exception;


public class ReplenishmentOverrunException extends ServiceException {
    public ReplenishmentOverrunException() {
    }

    public ReplenishmentOverrunException(String message) {
        super(message);
    }

    public ReplenishmentOverrunException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReplenishmentOverrunException(Throwable cause) {
        super(cause);
    }
}
