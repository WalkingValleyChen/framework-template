package com.chen.exception;

/**
 * @author Chenwl
 * @version 1.0.0
 */
public class RequestParamException extends BaseException {
    public RequestParamException() {
    }

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParamException(Throwable cause) {
        super(cause);
    }

    public RequestParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
