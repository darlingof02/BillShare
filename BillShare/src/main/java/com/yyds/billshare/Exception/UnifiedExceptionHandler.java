package com.yyds.billshare.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = FormInfoException.class)
    @ResponseBody
    public String handleException(FormInfoException formInfoException){

        String response="";
        ExceptionEnum exception= formInfoException.getException();
        switch (exception.getCode()){
            case 100:
                response = exception.getMessage();
                break;

        }
        return response;
    }
}
