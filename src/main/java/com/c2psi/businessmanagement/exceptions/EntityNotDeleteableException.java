package com.c2psi.businessmanagement.exceptions;

import java.util.List;

/****************************************************************************************
 * Pour les entites qui ne sont pas supprimable dans la base de données parcequils
 * imbriquent d'autres entites et que leurs suppression peut entrainer un enchainement
 ****************************************************************************************/
public class EntityNotDeleteableException extends BMException{
    public EntityNotDeleteableException(String message) {
        super(message);
    }

    public EntityNotDeleteableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotDeleteableException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause, errorCode);
    }

    public EntityNotDeleteableException(String message, Throwable cause, ErrorCode errorCode, List<String> errors) {
        super(message, cause, errorCode, errors);
    }

    public EntityNotDeleteableException(String message, ErrorCode errorCode, List<String> errors) {
        super(message, errorCode, errors);
    }

    public EntityNotDeleteableException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
