package com.flaviopessini.sbvendas.controllers;

import com.flaviopessini.sbvendas.exceptions.RegraNegocioException;
import com.flaviopessini.sbvendas.utils.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception) {
        final var message = exception.getMessage();
        return new ApiErrors(message);
    }
}
