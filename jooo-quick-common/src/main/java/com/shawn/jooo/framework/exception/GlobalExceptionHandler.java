package com.shawn.jooo.framework.exception;

import com.shawn.jooo.framework.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public Response exceptionHandler(ApiException e) {
        logger.error("global api exception:{}", e.getMessage());
        Response response = new Response();
        response.setCode(e.getErrorCode());
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        logger.error("global  exception:", e);
        Response response = new Response();
        response.setCode(ErrorCode.FAILED.getCode());
        response.setMessage(ErrorCode.FAILED.getMessage());
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response exceptionHandler(RuntimeException e) {
        logger.error("global runtime exception:", e);
        Response response = new Response();
        response.setCode(ErrorCode.FAILED.getCode());
        response.setMessage(ErrorCode.FAILED.getMessage());
        return response;
    }

}
