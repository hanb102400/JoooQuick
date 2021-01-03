package com.shawn.jooo.framework.exception;

public abstract class BaseRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errorCode;

    private String message;

    public BaseRuntimeException() {
        super();
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseRuntimeException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseRuntimeException(String message) {
        super(message);
        this.errorCode = 1000;
        this.message = message;
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
