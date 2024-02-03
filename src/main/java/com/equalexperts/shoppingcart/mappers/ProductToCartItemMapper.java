package com.equalexperts.shoppingcart.mappers;

import com.equalexperts.shoppingcart.models.CartItem;
import com.equalexperts.shoppingcart.models.ExternalProductApiResponse;

/**
 * Interface for mapping {@link ExternalProductApiResponse} to {@link CartItem}.
 *
 * @author yateshkumar
 */
public interface ProductToCartItemMapper {

    /**
     * Maps an external product response to a shopping cart item with the specified quantity.
     *
     * @param productDTO the external product response.
     * @param quantity   the quantity of the product in the shopping cart.
     * @return the mapped CartItem.
     */
    CartItem mapProductToCartItem(ExternalProductApiResponse productDTO, int quantity);
}
