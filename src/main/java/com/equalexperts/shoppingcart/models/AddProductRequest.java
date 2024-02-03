package com.equalexperts.shoppingcart.models;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

/**
 * Represents the request to add a product to the shopping cart.
 *
 * @author yateshkumar
 */
@Data
public class AddProductRequest {

    private String userAccountId;

    private String productName;

    private String quantity;
}
