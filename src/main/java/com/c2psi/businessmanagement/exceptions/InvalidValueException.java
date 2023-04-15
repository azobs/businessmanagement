package com.c2psi.businessmanagement.exceptions;

import java.util.List;

public class InvalidValueException extends BMException{

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidValueException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public InvalidValueException(String message, Throwable cause, ErrorCode errorCode, List<String> errors) {
        super(message, cause, errorCode, errors);
    }

    public InvalidValueException(String message, ErrorCode errorCode, List<String> errors) {
        super(message, errorCode, errors);
    }

    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
