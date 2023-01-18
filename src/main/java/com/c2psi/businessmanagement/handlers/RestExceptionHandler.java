package com.c2psi.businessmanagement.handlers;

import com.c2psi.businessmanagement.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.IllegalArgumentException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(EntityNotFoundException exception,
                                                    WebRequest webRequest){
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidEntityException exception,
                                                    WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> handleException(IllegalArgumentException exception,
                                                    WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto =  ErrorDto.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<ErrorDto> handleException(DuplicateEntityException exception,
                                                    WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.CONFLICT;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage()+":["+exception.getErrors()+"]")
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(NullArgumentException.class)
    public ResponseEntity<ErrorDto> handleException(NullArgumentException exception,
                                                    WebRequest webRequest){
        final HttpStatus badRequest = HttpStatus.CONFLICT;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage()+":["+exception.getErrors()+"]")
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }

}
