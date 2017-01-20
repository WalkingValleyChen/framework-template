package com.chen.exception;

/**
 * @author Chenwl
 * @version 1.0.0
 */
public class DataNotExistException extends BaseException {
    public DataNotExistException() {
    }

    public DataNotExistException(String message) {
        super(message);
    }

    public DataNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotExistException(Throwable cause) {
        super(cause);
    }

    public DataNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
