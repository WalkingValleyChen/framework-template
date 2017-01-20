package com.chen.exception;

/**
 * @author Chenwl
 * @version 1.0.0
 */
public class DataStatusFaultException extends BaseException{
    public DataStatusFaultException() {
    }

    public DataStatusFaultException(String message) {
        super(message);
    }

    public DataStatusFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataStatusFaultException(Throwable cause) {
        super(cause);
    }

    public DataStatusFaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
