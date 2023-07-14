package com.c2psi.businessmanagement.exceptions;

import java.util.List;

public class ZeroRoleForUserBMException extends BMException{
    public ZeroRoleForUserBMException(String message) {
        super(message);
    }

    public ZeroRoleForUserBMException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZeroRoleForUserBMException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public ZeroRoleForUserBMException(String message, Throwable cause, ErrorCode errorCode, List<String> errors) {
        super(message, cause, errorCode, errors);
    }

    public ZeroRoleForUserBMException(String message, ErrorCode errorCode, List<String> errors) {
        super(message, errorCode, errors);
    }

    public ZeroRoleForUserBMException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
