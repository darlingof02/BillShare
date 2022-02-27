package com.yyds.billshare.Exception;

public enum ExceptionEnum {
    // add new exceptions here
    SIGNUP_EMAIL_EXIST(100,"user email already exists"),
    FORM_ERROR(101,"form format error");
    final private Integer code;
    final private String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
