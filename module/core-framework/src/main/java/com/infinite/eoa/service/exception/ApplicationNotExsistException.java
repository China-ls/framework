package com.infinite.eoa.service.exception;

/**
 * @author hx on 16-7-27.
 * @since 1.0
 */
public class ApplicationNotExsistException extends ServiceException {
    public ApplicationNotExsistException(String message) {
        super(message);
    }

    public ApplicationNotExsistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationNotExsistException(Throwable cause) {
        super(cause);
    }

    public ApplicationNotExsistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
