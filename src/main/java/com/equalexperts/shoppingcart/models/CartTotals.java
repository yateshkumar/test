package com.equalexperts.shoppingcart.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents the totals of the shopping cart.
 *
 * @author yateshkumar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartTotals {

    /**
     * Cart subtotal represents sum of price for all items/products.
     */
    private BigDecimal subtotal;

    /**
     * Tax amount represents tax payable (charged at 12.5% on the subtotal)
     */
    private BigDecimal tax;

    /**
     * Total amount payable represents (subtotal + tax).
     */
    private BigDecimal total;
}