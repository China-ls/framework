package com.infinite.eoa.service.exception;

/**
 * @author hx on 16-7-27.
 * @since 1.0
 */
public class EntityExsistException extends ServiceException {
    public EntityExsistException(String message) {
        super(message);
    }

    public EntityExsistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExsistException(Throwable cause) {
        super(cause);
    }

    public EntityExsistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
