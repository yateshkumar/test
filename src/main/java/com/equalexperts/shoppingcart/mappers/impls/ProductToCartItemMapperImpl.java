package com.equalexperts.shoppingcart.mappers.impls;

import com.equalexperts.shoppingcart.mappers.ProductToCartItemMapper;
import com.equalexperts.shoppingcart.models.CartItem;
import com.equalexperts.shoppingcart.models.ExternalProductApiResponse;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link ProductToCartItemMapper} for mapping an external product response to a shopping cart item.
 *
 * @author yateshkumar
 */
@Component
public class ProductToCartItemMapperImpl implements ProductToCartItemMapper {

    /**
     * Maps an external product response to a shopping cart item with the specified quantity.
     *
     * @param product  the external product response.
     * @param quantity the quantity of the product in the shopping cart.
     * @return the mapped CartItem.
     */
    @Override
    public CartItem mapProductToCartItem(ExternalProductApiResponse product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setName(product.getTitle());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        return cartItem;
    }
}
