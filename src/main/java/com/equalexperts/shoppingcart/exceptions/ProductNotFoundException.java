package com.equalexperts.shoppingcart.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a product is not found in the shopping cart.
 *
 * @author yateshkumar
 */
public class ProductNotFoundException extends BaseShoppingCartException {

    /**
     * Constructs a ProductNotFoundException with the specified error message and parameter.
     *
     * @param errorMessage the error message.
     * @param parameter     the parameter to format into the error message.
     */
    public ProductNotFoundException(ErrorMessage errorMessage, String parameter) {
        super(errorMessage, parameter, HttpStatus.NOT_FOUND);
    }

    /**
     * Constructs a ProductNotFoundException with the specified error message and cause.
     *
     * @param errorMessage the error message.
     * @param cause         the cause of the exception.
     */
    public ProductNotFoundException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause, HttpStatus.NOT_FOUND);
    }
}