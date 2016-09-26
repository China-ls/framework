package com.infinite.eoa.router.entity;

import com.infinite.eoa.core.entity.AbstractEntity;

/**
 * @author by hx on 16-7-6.
 */
public class ErrorResponse extends AbstractEntity {
    private String uri;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String uri, String message) {
        this.uri = uri;
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "uri='" + uri + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}