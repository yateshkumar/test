package com.equalexperts.shoppingcart.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a validation fails in the shopping cart.
 *
 * @author yateshkumar
 */
public class ValidationFailedException extends BaseShoppingCartException {


    /**
     * Constructs a ValidationFailedException with the specified error message.
     *
     * @param errorMessage the error message.
     */
    public ValidationFailedException(ErrorMessage errorMessage) {
        super(errorMessage, HttpStatus.BAD_REQUEST);
    }

    /**
     * Constructs a ValidationFailedException with the specified error message and parameter.
     *
     * @param errorMessage the error message.
     * @param parameter     the parameter to format into the error message.
     */
    public ValidationFailedException(ErrorMessage errorMessage, String parameter) {
        super(errorMessage, parameter, HttpStatus.BAD_REQUEST);
    }

    /**
     * Constructs a ValidationFailedException with the specified error message and cause.
     *
     * @param errorMessage  the error message.
     * @param parameter      the parameter to format into the error message.
     * @param cause           the cause of the exception.
     */
    public ValidationFailedException(ErrorMessage errorMessage,String parameter, Throwable cause ) {
        super(errorMessage, parameter,cause, HttpStatus.BAD_REQUEST);
    }

    /**
     * Constructs a ValidationFailedException with the specified error message and cause.
     *
     * @param errorMessage  the error message.
     * @param param1        the parameter 1.
     * @param param2        the parameter 2.
     */
    public ValidationFailedException(ErrorMessage errorMessage,String param1, String param2) {
        super(errorMessage, param1,param2, HttpStatus.BAD_REQUEST);
    }

    /**
     * Constructs a ValidationFailedException with the specified error message, param1, param2 and cause.
     *
     * @param errorMessage  the error message.
     * @param param1        the parameter 1.
     * @param param2        the parameter 2.
     * @param cause         the cause of the exception.
     */
    public ValidationFailedException(ErrorMessage errorMessage,String param1, String param2, Throwable cause ) {
        super(errorMessage, param1,param2,cause, HttpStatus.BAD_REQUEST);
    }

}