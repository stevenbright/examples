package com.truward.web.gds.exceptions;

/**
 * Exception caused by illegal data manipulation.
 */
public class DaoException extends RuntimeException {
    public DaoException() {
    }

    public DaoException(String s) {
        super(s);
    }

    public DaoException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DaoException(Throwable throwable) {
        super(throwable);
    }
}
