package com.yyds.billshare.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "input type not allowed")
public class FormInfoException extends RuntimeException{

    final private ExceptionEnum exception;
    public FormInfoException(ExceptionEnum error){
        super(error.getMessage());
        this.exception = error;
    }

    public ExceptionEnum getException() {
        return exception;
    }
}
