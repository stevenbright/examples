package com.alexshabanov.restcomp.exposure.shared.model;

/**
 * Represents error description.
 */
public final class ErrorDescription {

    public static final int UNCLASSIFIED = 1100;

    public static final int UNSUPPORTED = 1021;

    private int errorCode;

    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
