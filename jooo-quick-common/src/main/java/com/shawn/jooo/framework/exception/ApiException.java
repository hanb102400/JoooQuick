package com.shawn.jooo.framework.exception;

public class ApiException extends BaseRuntimeException {

    public ApiException() {
        super();
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(int errorCode, String message) {
        super(errorCode, message);
    }

    public ApiException(String message) {
        super(message);
    }
}
