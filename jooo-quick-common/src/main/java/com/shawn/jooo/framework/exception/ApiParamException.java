package com.shawn.jooo.framework.exception;


/**
 *
 * @author shawn
 */
public class ApiParamException extends ApiException {

    private String param;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public ApiParamException() {
        super();
    }

    public ApiParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiParamException(int errorCode, String message) {
        super(errorCode, message);
    }

    public ApiParamException(String message) {
        super(message);
    }

    public ApiParamException(String param, String message, Throwable cause) {
        super(message, cause);
        if (param != null) {
            this.param = param;
            this.setMessage(message + " [" + param + "]");
        }

    }

    public ApiParamException(String param, int errorCode, String message) {
        super(errorCode, message);
        if (param != null) {
            this.param = param;
            this.setMessage(message + " [" + param + "]");
        }
    }

    public ApiParamException(String param, String message) {
        super(message);
        if (param != null) {
            this.param = param;
            this.setMessage(message + " [" + param + "]");
        }
    }
}
