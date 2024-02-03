package com.equalexperts.shoppingcart.models;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Represents the response from an external product API.
 *
 * @author yateshkumar
 */
@Data
public class ExternalProductApiResponse {

    private String title;
    private BigDecimal price;
}
