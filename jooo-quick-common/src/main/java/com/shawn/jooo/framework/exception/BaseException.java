package com.shawn.jooo.framework.exception;

public abstract class BaseException extends Exception {

    private static final long serialVersionUID = 1L;

    private int errorCode;

    private String message;

    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseException(String message) {
        super(message);
        this.errorCode = 1000;
        this.message = message;
    }

    public BaseException(Throwable cause) {
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


