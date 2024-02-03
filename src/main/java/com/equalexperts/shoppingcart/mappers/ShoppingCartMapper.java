package com.equalexperts.shoppingcart.mappers;

import com.equalexperts.shoppingcart.models.*;

/**
 * Interface for mapping shopping cart.
 *
 * @author yateshkumar
 */
public interface ShoppingCartMapper {

    /** Maps {@link ShoppingCart} & {@link CartTotals} to {@link ShoppingCartDTO}.
     *
     * @param shoppingCart shopping cart entity.
     * @param cartTotals cart totals that includes subtotal, tax and total
     * @return the mapped ShoppingCartDTO
     */
    ShoppingCartDTO mapToShoppingCartDTO(ShoppingCart shoppingCart, CartTotals cartTotals);
}
