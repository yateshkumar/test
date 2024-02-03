package com.equalexperts.shoppingcart.services;

import com.equalexperts.shoppingcart.exceptions.BaseShoppingCartException;
import com.equalexperts.shoppingcart.models.AddProductRequest;
import com.equalexperts.shoppingcart.models.ShoppingCart;
import com.equalexperts.shoppingcart.models.ShoppingCartDTO;

/**
 * Service interface for managing shopping cart operations.
 * This interface defines operations related to the shopping cart.
 *
 * @author yateshkumar
 */
public interface ShoppingCartService {

    /**
     * Add a product to the shopping cart.
     *
     * @param addProductRequest add product request representing the product and quantity to be added.
     * @return the current state of the shopping cart.
     * @throws BaseShoppingCartException if there is an issue adding the product to the cart.
     */
    ShoppingCartDTO addProductToShoppingCart(AddProductRequest addProductRequest);

    /**
     * Retrieve the current state of the shopping cart.
     *
     * @param userAccountId the account ID of the logged-in user.
     * @return the current state of the shopping cart.
     * @throws BaseShoppingCartException if there is an issue calculating the cart totals.
     */
    ShoppingCartDTO getShoppingCart(String userAccountId);
}