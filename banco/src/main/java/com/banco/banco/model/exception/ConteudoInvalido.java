package com.banco.banco.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ConteudoInvalido extends RuntimeException {

    public ConteudoInvalido(String message){
        super(message);
    }
}
