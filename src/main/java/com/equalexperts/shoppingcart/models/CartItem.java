package com.equalexperts.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Entity class representing an item in the shopping cart.
 *
 * @author yateshkumar
 */
@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Name of the item
    private String name;

    // Price of the product
    private BigDecimal price;

    // Quantity of the product
    private int quantity;

    @ManyToOne
    private ShoppingCart shoppingCart;
}