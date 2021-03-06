package com.careem.voice.notes.service.configs;

import com.careem.voice.notes.service.controllers.utils.ApiResponse;
import javassist.NotFoundException;
import lombok.extern.log4j.Log4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*Exception handling class to catch the exception thrown and display the suitable message*/
@Log4j
@RestControllerAdvice
public class ExceptionHandling{

    private static final String DATA_ALREADY_EXISTS = "Data already exist";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataAccessIntegrityViolationException(DataIntegrityViolationException ex) {
        List<String> errors = new ArrayList<>();
        if (ex.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cause = (ConstraintViolationException) ex.getCause();
            String constrainViolationMsg = "Constrain violation in " + cause.getConstraintName();
            String msg = cause.getSQLException().getMessage();
            errors.add(msg);
            log.error(msg, ex);
            ResponseEntity responseEntity = new ResponseEntity(new ApiResponse(HttpStatus.CONFLICT, false, DATA_ALREADY_EXISTS + ", " + constrainViolationMsg,  errors), HttpStatus.CONFLICT);
            return responseEntity;
        }
        String msg = INTERNAL_SERVER_ERROR + ex.getMessage();
        log.error(msg, ex);
        errors.add(msg);
        return new ResponseEntity(new ApiResponse(HttpStatus.CONFLICT, false, INTERNAL_SERVER_ERROR, errors), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleRecordNotFoundException(NotFoundException ex) {
        return new ResponseEntity(new ApiResponse(HttpStatus.NOT_FOUND, false, ex.getMessage(), (Object)null, (List)null), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("Unexpected Error", ex);
        return new ResponseEntity(new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, INTERNAL_SERVER_ERROR, (Object)null,  Arrays.asList(ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
