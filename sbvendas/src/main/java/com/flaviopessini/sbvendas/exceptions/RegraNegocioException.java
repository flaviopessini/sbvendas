package com.flaviopessini.sbvendas.exceptions;

/**
 * Exception personalizada para regra de negócio.
 */
public class RegraNegocioException extends RuntimeException {

    public RegraNegocioException(String message) {
        super(message);
    }
}
