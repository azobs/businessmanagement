package com.c2psi.businessmanagement.exceptions;


import java.util.List;

/************************************************************************************
 * Pour les entites qui ne sont pas trouver dans la base de données apres recherche
 ************************************************************************************/
public class EntityNotFoundException extends BMException {

    public EntityNotFoundException(String message){
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public EntityNotFoundException(String message, Throwable cause, ErrorCode errorCode){
        super(message, cause, errorCode);
    }

    public EntityNotFoundException(String message, ErrorCode errorCode){
        super(message, errorCode);
    }

    public EntityNotFoundException(String msg, ErrorCode errorCode, List<String> errors){
        super(msg, errorCode, errors);
    }

}
