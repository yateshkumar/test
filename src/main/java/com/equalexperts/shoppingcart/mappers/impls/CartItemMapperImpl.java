package com.equalexperts.shoppingcart.mappers.impls;

import com.equalexperts.shoppingcart.mappers.CartItemMapper;
import com.equalexperts.shoppingcart.models.CartItem;
import com.equalexperts.shoppingcart.models.CartItemDTO;
import org.springframework.stereotype.Component;

/** Implementation of {@link CartItemMapper}
 *
 * @author yateshkumar
 */
@Component
public class CartItemMapperImpl implements CartItemMapper {

    /** Maps {@link CartItem} to {@link CartItemDTO}
     *
     * @param cartItem cart items entity.
     * @return mapped CartItemDTO
     */
    @Override
    public CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setName(cartItem.getName());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setPrice(cartItem.getPrice());
        return  cartItemDTO;
    }
}
