package com.yyds.billshare.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnifiedExceptionHandler {

    @ExceptionHandler(value = FormInfoException.class)
    @ResponseBody
    public ResponseEntity handleException(FormInfoException formInfoException){

        ResponseEntity<?> responseEntity = new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        ExceptionEnum exception= formInfoException.getException();
        switch (exception.getCode()){
            case 100:
                responseEntity = new ResponseEntity<String>(exception.getMessage(),HttpStatus.ACCEPTED);
                break;
            case 101:
                responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.ACCEPTED);
                break;
        }
        return responseEntity;
    }
}
