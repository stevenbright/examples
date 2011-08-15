package com.truward.gbp.shared.service;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Represents service exception
 */
public class ServiceException extends Exception implements IsSerializable {
    private int errorCode;

    public ServiceException() {
    }

    public ServiceException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ServiceException(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getReadableMessage() {
        switch (errorCode) {
            case Errors.INVALID_CREDENTIALS:
                return "Invalid credentials";
            default:
                return "Internal error";
        }
    }
}
