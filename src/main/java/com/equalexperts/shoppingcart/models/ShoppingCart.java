package com.equalexperts.shoppingcart.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

/**
 * Entity class representing a shopping cart.
 *
 * @author yateshkumar
 */
@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCart {

    /**
     * Unique identifier for the user's account associated with the shopping cart.
     */
    @Id
    private Long userAccountId;

    /**
     * Set of cart items associated with the shopping cart.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    /**
     * Adds a set of cart items to the shopping cart.
     *
     * @param cartItems the set of cart items to be added.
     */
    public void addCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}