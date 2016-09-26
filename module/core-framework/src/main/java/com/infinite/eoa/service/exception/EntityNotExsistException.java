package com.infinite.eoa.service.exception;

/**
 * @author hx on 16-7-27.
 * @since 1.0
 */
public class EntityNotExsistException extends ServiceException {
    public EntityNotExsistException(String message) {
        super(message);
    }

    public EntityNotExsistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotExsistException(Throwable cause) {
        super(cause);
    }

    public EntityNotExsistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
