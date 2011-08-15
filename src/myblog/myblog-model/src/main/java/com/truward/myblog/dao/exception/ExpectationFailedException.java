package com.truward.myblog.dao.exception;

public final class ExpectationFailedException extends RuntimeException {
    public ExpectationFailedException() {
    }

    public ExpectationFailedException(String s) {
        super(s);
    }

    public ExpectationFailedException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ExpectationFailedException(Throwable throwable) {
        super(throwable);
    }
}
