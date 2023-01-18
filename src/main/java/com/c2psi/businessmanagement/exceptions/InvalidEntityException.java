package com.c2psi.businessmanagement.exceptions;


import java.util.List;

/****************************************************************************
 * Pour les entites que ne sont pas valide lors de leurs enregistrements
 * ou de leurs mises a jour dans la base de données
 ****************************************************************************/
public class InvalidEntityException extends BMException {

    public InvalidEntityException(String msg){
        super(msg);
    }

    public InvalidEntityException(String msg, Throwable cause){
        super(msg, cause);
    }

    public InvalidEntityException(String msg, Throwable cause, ErrorCode errorCode){

        super(msg, cause, errorCode);
    }

    public InvalidEntityException(String msg, Throwable cause, ErrorCode errorCode, List<String> errors){
        super(msg, cause, errorCode, errors);
    }

    public InvalidEntityException(String msg, ErrorCode errorCode, List<String> errors) {
        super(msg, errorCode, errors);
    }
    public InvalidEntityException(String msg, ErrorCode errorCode) {
        super(msg, errorCode);
    }
}
