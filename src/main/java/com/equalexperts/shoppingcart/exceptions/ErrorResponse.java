package com.equalexperts.shoppingcart.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Class representing the structure of an error response.
 *
 * @author yateshkumar
 */
@Getter
@Setter
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;

    /**
     * Constructs an ErrorResponse with the specified status, error, and message.
     *
     * @param status  the HTTP status code.
     * @param error   a brief description of the error.
     * @param message a detailed error message.
     */
    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}

