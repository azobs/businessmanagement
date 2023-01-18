package com.c2psi.businessmanagement.exceptions;


import java.util.List;

public class DuplicateEntityException extends BMException {

    public DuplicateEntityException(String msg){
        super(msg);
    }

    public DuplicateEntityException(String msg, Throwable cause){
        super(msg, cause);
    }

    public DuplicateEntityException(String msg, Throwable cause, ErrorCode errorCode){
        super(msg, cause, errorCode);
    }

    public DuplicateEntityException(String msg, ErrorCode errorCode){
        super(msg, errorCode);
    }

    public DuplicateEntityException(String msg, ErrorCode errorCode, List<String> errors){
        super(msg, errorCode, errors);
    }
}
