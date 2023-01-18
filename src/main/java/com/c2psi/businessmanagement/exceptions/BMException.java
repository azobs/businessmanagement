package com.c2psi.businessmanagement.exceptions;

import lombok.Getter;

import java.util.List;

public class BMException extends RuntimeException{
    @Getter
    private ErrorCode errorCode;
    @Getter
    private List<String> errors;
    @Getter
    private String message;

    public BMException(String message) {
        super(message);
        this.message = message;
    }

    public BMException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BMException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.message = message;
        this.errorCode = errorCode;
    }

    public BMException(String message, Throwable cause, ErrorCode errorCode, List<String> errors) {
        super(message, cause);
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public BMException(String message, ErrorCode errorCode, List<String> errors) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
    }

    public BMException(String message, ErrorCode errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }

}
