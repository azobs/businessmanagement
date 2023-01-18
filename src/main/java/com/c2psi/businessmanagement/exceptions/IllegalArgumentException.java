package com.c2psi.businessmanagement.exceptions;



import java.util.List;

/****************************************************************************
 * Pour les paramètres de recherche non valide lors des recherches
 ****************************************************************************/
public class IllegalArgumentException extends BMException {

    public IllegalArgumentException(String msg){
        super(msg);
    }

    public IllegalArgumentException(String msg, Throwable cause){
        super(msg, cause);
    }

    public IllegalArgumentException(String msg, Throwable cause, ErrorCode errorCode){
        super(msg, cause, errorCode);
    }

    public IllegalArgumentException(String msg, ErrorCode errorCode){
        super(msg, errorCode);
    }

    public IllegalArgumentException(String msg, ErrorCode errorCode, List<String> errors){
        super(msg, errorCode, errors);
    }
}
