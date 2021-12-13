package com.example.myretail.exception;

// Generic exception created to handle any errors in the API
// We can also create different exceptions based on specific functionality and based on the requirements.
public class GenericApiErrorException extends RuntimeException {

    public GenericApiErrorException(String msg, Exception e){
        super(msg);
    }

}
