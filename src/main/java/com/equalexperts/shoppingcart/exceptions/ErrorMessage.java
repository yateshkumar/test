package com.equalexperts.shoppingcart.exceptions;

/**
 * Enum representing error messages for shopping cart exceptions.
 *
 * @author yateshkumar
 */
public enum ErrorMessage {

    /**
     * Error message for an empty shopping cart.
     */
    SHOPPING_CART_EMPTY("Shopping cart is empty."),

    /**
     * Error message for an invalid quantity provided.
     */
    INVALID_PARAMETER_VALUE("Invalid value for parameter '%s' : '%s'"),

    MAX_ALLOWED_QUANTITY_EXCEED("Provided quantity: '%s' is greater than maximum allowed : '%s'"),

    /**
     * Error message when a product is not found in the catalog.
     */
    PRODUCT_NOT_FOUND("Product: %S not found in the catalog."),

    /**
     * Error message for a missing mandatory field.
     */
    MANDATORY_FIELD_NOT_FOUND("Provide mandatory field: '%s'."),

    /**
     * Error message when an add product request is not found.
     */
    ADD_PRODUCT_REQUEST_NOT_FOUND("Provide add product request."),

    /**
     * Generic error message for external API issues.
     */
    EXTERNAL_API_ERROR("Error while calling external API."),

    /**
     * Generic error message for issues while retrieving product information.
     */
    GET_PRODUCT_GENERIC_ERROR("Error while retrieving product information.");

    private final String message;

    /**
     * Constructs an ErrorMessage with the specified message.
     *
     * @param message the error message.
     */
    ErrorMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the error message.
     *
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }
}