package com.yyds.billshare.Exception;

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
