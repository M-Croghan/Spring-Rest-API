package com.api.recipeapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Notifies engineer / end-point the requested information already exists.
@ResponseStatus(HttpStatus.CONFLICT)
public class InformationExistException extends RuntimeException{
    public InformationExistException(String message){
        super(message);
    }

}
