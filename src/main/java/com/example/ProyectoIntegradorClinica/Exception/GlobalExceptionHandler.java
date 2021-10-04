package com.example.ProyectoIntegradorClinica.Exception;

import org.apache.log4j.Logger;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> allErrors(Exception ex, WebRequest req){
        logger.error(ex.getMessage());
        return new ResponseEntity("Error: "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> errorsByBadRequest(BadRequestException ex, WebRequest req) {
        logger.error(ex.getMessage());
        return new ResponseEntity("Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> errorsByResourceNotFound(ResourceNotFoundException ex, WebRequest req){
        logger.error(ex.getMessage());
        return new ResponseEntity("Error: "+ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
