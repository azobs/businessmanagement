package com.c2psi.businessmanagement.handlers;

import com.c2psi.businessmanagement.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*******************************************************************************************
 * @RestControllerAdvice signifie que lors de l'appel des services a partir du controller,
 * toutes les exceptions retourne pendant ces appels seront interceptees directement par
 * cette class.
 * Maintenant si une methode annote @ExceptionHandler fait allusion a cette exception
 * la methode correspondante sera excecute pour ainsi traiter l'exception afin de retourner
 * a l'Appelant un message d'erreur assez claire.
 *
 * Donc seul les exceptions non prevu par l'application donneront lieu a des messages d'erreur vague
 */
@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(BMException.class)
    public ResponseEntity<ErrorDto> handleException(BMException exception,
                                                    WebRequest webRequest){
        log.info("A BMException is launch on the server side means it is not correspond to any specific exception " +
                "that our application can throw");
        final HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorDto> handleException(DuplicateEntityException exception,
                                                    WebRequest webRequest){
        log.info("A DuplicateEntityException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.CONFLICT;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null):null)
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(EntityNotDeleteableException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotDeleteableException exception,
                                                    WebRequest webRequest){
        log.info("A EntityNotDeleteableException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.CONFLICT;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception,
                                                    WebRequest webRequest){
        log.info("A EntityNotFoundException is launch on the server side");
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception,
                                                    WebRequest webRequest){
        log.info("A InvalidEntityException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<ErrorDto> handleException(NullArgumentException exception,
                                                    WebRequest webRequest){
        log.info("A NullArgumentException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.NOT_ACCEPTABLE;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

}
