package com.banco.banco.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class RegisterNull extends RuntimeException{
    public RegisterNull(String message){
        super(message);
    }
}
