package com.shawn.jooo.admin.exeption;

import com.shawn.jooo.framework.constant.ErrorCode;
import com.shawn.jooo.framework.core.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        logger.error("global  exception:", e);
        Response response = new Response();
        response.setCode(ErrorCode.FAILED.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response exceptionHandler(RuntimeException e) {
        logger.error("global runtime exception:", e);
        Response response = new Response();
        response.setCode(ErrorCode.FAILED.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

}
