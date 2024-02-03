package com.equalexperts.shoppingcart.mappers;

import com.equalexperts.shoppingcart.models.CartItem;
import com.equalexperts.shoppingcart.models.CartItemDTO;

/**
 * Interface for mapping Cart item.
 *
 * @author yateshkumar
 */
public interface CartItemMapper {

    /** Maps {@link CartItem} to {@link CartItemDTO}
     *
     * @param cartItem cart items entity.
     * @return mapped CartItemDTO
     */
    CartItemDTO mapToCartItemDTO(CartItem cartItem);
}
