package com.example.myretail.exception.response;

import com.example.myretail.exception.GenericApiErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // This method handles the Custom exception (GenericApiErrorException) and returns a generic response accordingly.
    // We can create multiple custom exceptions based on the requirements.
    @ExceptionHandler(value = {GenericApiErrorException.class})
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException ex, WebRequest webRequest){
        ExceptionResponse response = new ExceptionResponse();

        response.setMsg("Application Error - " + ex.getMessage());
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
