package com.equalexperts.shoppingcart.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Base exception class for shopping cart-related exceptions.
 *
 * @author yateshkumar
 */
@Getter
@Setter
public class BaseShoppingCartException extends RuntimeException {

    private ErrorMessage errorMessage;

    private HttpStatus httpStatus;

    /**
     * Constructs a BaseShoppingCartException with the specified error message and HTTP status.
     *
     * @param errorMessage the error message.
     * @param httpStatus   the HTTP status associated with the exception.
     */
    public BaseShoppingCartException(ErrorMessage errorMessage, HttpStatus httpStatus) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a BaseShoppingCartException with the specified error message, cause, and HTTP status.
     *
     * @param errorMessage the error message.
     * @param cause         the cause of the exception.
     * @param httpStatus   the HTTP status associated with the exception.
     */
    public BaseShoppingCartException(ErrorMessage errorMessage, Throwable cause, HttpStatus httpStatus) {
        super(errorMessage.getMessage(),cause);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a BaseShoppingCartException with the specified error message, parameter, and HTTP status.
     *
     * @param errorMessage the error message.
     * @param parameter     the parameter to format into the error message.
     * @param httpStatus   the HTTP status associated with the exception.
     */
    public BaseShoppingCartException(ErrorMessage errorMessage, String parameter, HttpStatus httpStatus) {
        super(String.format(errorMessage.getMessage(),parameter));
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a BaseShoppingCartException with the specified error message, parameter, cause, and HTTP status.
     *
     * @param errorMessage the error message.
     * @param parameter     the parameter to format into the error message.
     * @param throwable     the cause of the exception.
     * @param httpStatus   the HTTP status associated with the exception.
     */
    public BaseShoppingCartException(ErrorMessage errorMessage, String parameter, Throwable throwable,
                                     HttpStatus httpStatus) {
        super(String.format(errorMessage.getMessage(),parameter),throwable);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a BaseShoppingCartException with the specified error message, param1, param2, and HTTP status.
     *
     * @param errorMessage the error message.
     * @param param1        parameter 1.
     * @param param2        parameter 2.
     * @param httpStatus    the HTTP status associated with the exception.
     */
    public BaseShoppingCartException(ErrorMessage errorMessage, String param1, String param2,
                                     HttpStatus httpStatus) {
        super(String.format(errorMessage.getMessage(),param1,param2));
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a BaseShoppingCartException with the specified error message, param1, param2, cause, and HTTP status.
     *
     * @param errorMessage the error message.
     * @param param1        name of the parameter.
     * @param param2        value of the parameter.
     * @param throwable     the cause of the exception.
     * @param httpStatus    the HTTP status associated with the exception.
     */
    public BaseShoppingCartException(ErrorMessage errorMessage, String param1, String param2,
                                     Throwable throwable, HttpStatus httpStatus) {
        super(String.format(errorMessage.getMessage(),param1,param2),throwable);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }


}