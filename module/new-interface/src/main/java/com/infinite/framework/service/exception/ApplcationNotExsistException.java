package com.infinite.framework.service.exception;

/**
 * @author hx on 16-7-27.
 * @since 1.0
 */
public class ApplcationNotExsistException extends ServiceException {
    public ApplcationNotExsistException(String message) {
        super(message);
    }

    public ApplcationNotExsistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplcationNotExsistException(Throwable cause) {
        super(cause);
    }

    public ApplcationNotExsistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
