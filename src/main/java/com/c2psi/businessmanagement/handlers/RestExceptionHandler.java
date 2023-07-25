package com.c2psi.businessmanagement.handlers;

import com.c2psi.businessmanagement.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

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
    public ResponseEntity<?> handleException(BMException exception,
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
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        map.put("message", "Some problems occurs during execution: Internal Server Error");
        map.put("data", errorDto);
        map.put("cause", "Des exceptions ont ete lance pendant l'execution de la methode. consulter le errorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>(errorDto, badRequest);

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
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.CONFLICT);
        map.put("message", "The entity sent is already exist with the same identity in the DB: CONFLICT");
        map.put("data", errorDto);
        map.put("cause", "Une entite est deja enregistre en BD avec les memes identifiant. Consulter le ErrorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.CONFLICT);
        //return new ResponseEntity<>(errorDto, badRequest);
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
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.CONFLICT);
        map.put("message", "The entity is strongly linked with another one and can't be deleated: CONFLICT");
        map.put("data", errorDto);
        map.put("cause", "Cette entite est lie avec une autre et ne peut etre supprime de la BD. Consulter le ErrorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.CONFLICT);
        //return new ResponseEntity<>(errorDto, badRequest);
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
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.NOT_FOUND);
        map.put("message", "The entity is not found in the DB: NOT_FOUND");
        map.put("data", errorDto);
        map.put("cause", "L'entite dont les identifiants sont envoyes n'existe pas en BD. Consulter le ErrorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.NOT_FOUND);
        //return new ResponseEntity<>(errorDto, notFound);
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
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", "The entity is not valid and the operation cannot be process: BAD_REQUEST");
        map.put("data", errorDto);
        map.put("cause", "L'entite n'est pas valide et l'operation ne peut etre realisee. Consulter le ErrorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorDto> handleException(InvalidValueException exception,
                                                    WebRequest webRequest){
        log.info("A InvalidValueException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.BAD_REQUEST);
        map.put("message", "The entity is not valid and the operation cannot be process: BAD_REQUEST");
        map.put("data", errorDto);
        map.put("cause", "L'entite n'est pas valide et l'operation ne peut etre realisee. Consulter le ErrorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        //return new ResponseEntity<>(errorDto, badRequest);
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
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.NOT_ACCEPTABLE);
        map.put("message", "Parameters are null and the needed operation can't be process: NOT_ACCEPTABLE");
        map.put("data", errorDto);
        map.put("cause", "Des parametres sont nuls et l'operation souhaitee ne peut donc etre traitee. Consulter le ErrorDto " +
                "pour plus de details");
        return new ResponseEntity(map, HttpStatus.NOT_ACCEPTABLE);
        //return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(UploadDownloadFilesException.class)
    public ResponseEntity<ErrorDto> handleException(UploadDownloadFilesException exception,
                                                    WebRequest webRequest){
        log.info("A UploadDownloadFilesException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorDto errorDto =  ErrorDto.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        map.put("message", "Problem at the server side during uploading or dowmloading a resource: INTERNAL_SERVER_ERROR");
        map.put("data", errorDto);
        map.put("cause", "Des problemes de lecture ou d'ecriture sur le serveur pendant la procedure d'upload ou de " +
                "download des fichiers");
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
        //return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(UsernameNotFoundException exception,
                                                    WebRequest webRequest){
        log.info("A UsernameNotFoundException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.UNAUTHORIZED;
        final ErrorDto errorDto =  ErrorDto.builder()
                //.errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                //.errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.UNAUTHORIZED);
        map.put("message", "Any user exist with the userToken sent: UNAUTHORIZED");
        map.put("data", errorDto);
        map.put("cause", "Aucun utilisateur en BD n'existe avec la chaine de connexion envoye. Elle ne correspond ni " +
                "a un email ni a un cni number ni a un login");
        return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
        //return new ResponseEntity<>(errorDto, badRequest);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDto> handleException(BadCredentialsException exception,
                                                    WebRequest webRequest){
        log.info("A BadCredentialsException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.UNAUTHORIZED;
        final ErrorDto errorDto =  ErrorDto.builder()
                //.errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                //.errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.UNAUTHORIZED);
        map.put("message", "Username and/or password sent is incorrect: UNAUTHORIZED");
        map.put("data", errorDto);
        map.put("cause", "La chaine de connexion et/ou le mot de passe saisi est incorrect");
        return new ResponseEntity(map, HttpStatus.UNAUTHORIZED);
        //return new ResponseEntity<>(errorDto, badRequest);
    }

    //ZeroRoleForUserBMException
    @ExceptionHandler(ZeroRoleForUserBMException.class)
    public ResponseEntity<ErrorDto> handleException(ZeroRoleForUserBMException exception,
                                                    WebRequest webRequest){
        log.info("A ZeroRoleForUserBMException is launch on the server side");
        final HttpStatus badRequest = HttpStatus.PRECONDITION_FAILED;
        final ErrorDto errorDto =  ErrorDto.builder()
                //.errorCode(exception.getErrorCode())
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                //.errorList(exception.getErrors()!=null?(!exception.getErrors().isEmpty()?exception.getErrors().stream().toList():null):null)
                .build();
        Map<String, Object> map = new LinkedHashMap<>();
        map.clear();
        map.put("status", HttpStatus.PRECONDITION_FAILED);
        map.put("message", "The userBM must have roles before being connected. Assign him a set of role and try again");
        map.put("data", errorDto);
        map.put("cause", "Le UserBM doit d'abord avoir des roles avant de se connecter");
        return new ResponseEntity(map, HttpStatus.PRECONDITION_FAILED);
        //return new ResponseEntity<>(errorDto, badRequest);
    }

}
