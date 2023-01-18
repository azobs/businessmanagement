package com.c2psi.businessmanagement.exceptions;


import java.util.List;

public class NullArgumentException extends BMException{

    public NullArgumentException(String message){
        super(message);
    }

    public NullArgumentException(String message, Throwable cause){
        super(message, cause);
    }

    public NullArgumentException(String message, Throwable cause, ErrorCode errorCode){
        super(message, cause, errorCode);
    }

    public NullArgumentException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }

    public NullArgumentException(String msg, ErrorCode errorCode, List<String> errors) {
        super(msg, errorCode, errors);
    }
}
