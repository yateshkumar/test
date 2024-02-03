package com.equalexperts.shoppingcart.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global exception handler for the shopping cart application.
 *
 * @author yateshkumar
 */
@ControllerAdvice
public class ShoppingCartExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartExceptionHandler.class);

    /**
     * Handles BaseShoppingCartException and its subclasses, returning a
     * ResponseEntity with the error message.
     *
     * @param ex the BaseShoppingCartException or its subclass
     * @return ResponseEntity with the error message and HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(BaseShoppingCartException.class)
    public ResponseEntity<ErrorResponse> handleShoppingCartException(BaseShoppingCartException ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus().value(),
                ex.getHttpStatus().getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    /**
     * Handles other types of exceptions, returning a ResponseEntity
     * with a generic error message and HttpStatus.INTERNAL_SERVER_ERROR.
     *
     * @param ex the exception
     * @return ResponseEntity with a generic error message and HttpStatus.INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logError(ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private void logError(Exception ex) {
        if(ex.getMessage().equalsIgnoreCase(ErrorMessage.SHOPPING_CART_EMPTY.getMessage())){
            logger.warn(ErrorMessage.SHOPPING_CART_EMPTY.getMessage());
            return;
        }
        logger.error("Error occurred: {}", ex.getMessage(), ex);
    }
}